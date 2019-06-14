#!/usr/bin/env python
# coding: utf-8

# In[1]:


import dask.dataframe as dd
from dask import compute


# In[2]:


df = dd.read_csv('../../Youtube*.csv')


# In[3]:


compute(df.columns)


# In[4]:


compute(df.shape)


# In[5]:


compute(df['CLASS'].value_counts())


# In[6]:


df['CONTENT'] = df['CONTENT'].str.lower()


# In[8]:


contains_check = df[df['CONTENT'].str.contains('check')]


# In[9]:


compute(contains_check['CLASS'].value_counts())

