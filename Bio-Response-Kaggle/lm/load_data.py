import pandas as pd
from numpy import ones
#from sklearn.model_selection import train_test_split

## KAGGLE bioresponse https://www.kaggle.com/c/bioresponse#Evaluation

train_url = '../data/train.csv'
test_url = '../data/test.csv' ## ignoring-- it doesn't have 'Activity' 

df_ = pd.read_csv(train_url)
df_test = pd.read_csv(test_url) # doesn't have 'Activity'
assert all([x==0 for x in df_.isna().sum().values])
assert all([pd.api.types.is_numeric_dtype(df_[feat]) for feat in df_.columns])
dependent='Activity'

df_['ones'] = ones(df_.shape[0])

'''X_train, X_test, y_train, y_test = train_test_split(df_.drop([dependent, 'ones'], axis=1), 
                                                    df_[dependent], 
                                                    train_size=0.8, test_size=0.2)
'''

X=df_.drop(dependent, axis=1).to_numpy()
y=df_[dependent].to_numpy()
