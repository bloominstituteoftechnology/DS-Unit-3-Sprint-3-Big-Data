from typing import List, Union 
from numbers import Number
from numba import njit, jit
import pandas as pd
import numpy as np
from load_data import X,y
import vali

class LogisticRegression: 
    def __init__(self, dat=X, target=y):
        self.X = dat
        self.y = target
    
    #@jit
    def center(self):
        ''' optional; centers the data at zero, both mutating and returning ''' 
        n = self.X.shape[1]
        means = np.array([[x.mean() for x in X.T] for _ in X])
        self.X = self.X - means
        return self.X

    @jit
    def logit(self, p: vali.Probability) -> vali.R:
        p = vali.Pr(p)
        return np.log(np.divide(p, 1-p))

    @jit
    def squish(x: vali.R) -> vali.Probability:
        return np.divide(1, 1+np.exp(-x))

    @jit
    def squishy(x: vali.R) -> vali.Probability: 
        return np.divide(np.exp(x), 1+np.exp(x))

    @jit
    def y_hat(self, beta: vali.Vector) -> vali.Vector:
        ''' the linear model --- i.e. logit applied to the predictions. '''
        return np.matmul(self.X, beta)

    @jit
    def MSE(self, beta):
        ''' mean squared error ''' 
        return np.divide(sum([(yy-yh)**2 
            for yy, yh in zip(self.y, self.y_hat(beta))]), self.X.shape[0])

class LRSolver(LogisticRegression): 
    ''' Reweighted Least Squares solver ''' 
    def __init__(self, dat=X, target=y, N_iters=25): 
        super().__init__(dat,target)
        self.N_iters = N_iters
        self.beta = np.ones(self.X.shape[1])

    @jit
    def p(self, beta: vali.Vector) -> vali.Vector:
        ''' squished linear model, aka, probabilities '''
        #return [self.squish(x) for x in self.y_hat(beta)]
        return np.divide(1, 1+np.exp(-self.y_hat(beta)))

    @jit
    def update(self, beta: vali.Vector) -> vali.Vector:
        pks = self.p(beta)
        W = np.diag(pks*(1-pks))

        fact0 = np.matmul(self.X.T, W)
        fact1 = np.matmul(fact0, self.X)
        fact2 = np.linalg.inv(fact1)
        fact3 = np.matmul(fact2, fact0)
        return np.matmul(fact3, self.y)

    @jit
    def predict(self) -> vali.Vector: 
        for _ in range(self.N_iters):
            self.beta = self.update(self.beta)
        return self.p(self.beta)


