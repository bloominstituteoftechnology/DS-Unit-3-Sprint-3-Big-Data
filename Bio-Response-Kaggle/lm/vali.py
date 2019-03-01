'''types and validation''' 
from numbers import Number
from typing import List, Union
import numpy as np 

R = np.float_
Probability = np.float_
Matrix = np.ndarray
Vector = np.ndarray

def Pr(self, x: Number) -> Probability: 
    assert 0<=x<=1  # for reasons of numerical accuracy, we need to make this "or equal to" unfortunately   
    return Probability(x)


