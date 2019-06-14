#!/usr/bin/env python
# coding: utf-8

# In[1]:


import dask.dataframe as dd
from dask.distributed import Client


# In[39]:


# Read all csv files
df = dd.read_csv('Youtube*.csv')


# In[40]:


# Rows
len(df.index)


# In[41]:


# Columns
len(df.columns)


# In[42]:


df.head()


# In[43]:


# Compute spam - 1 is spam and 0 is legit comment
df['CLASS'].value_counts().compute()


# In[44]:


# Before changing to lowercase there is 205 check in CONTENT
df['CONTENT'].str.contains('check').value_counts().compute()


# In[46]:


# After using .lower() method to change the CONTENT to all lower.
df['CONTENT'] = df['CONTENT'].str.lower()

# There is 480 instances of lowercase "check"
df['CONTENT'].str.contains('check').value_counts().compute()


# In[63]:


# Save df to spam
df_spam = df[df['CLASS'] == 1]

# 461 spam comments with the word 'check'
df_spam['CONTENT'].str.contains('check').value_counts().compute()


# In[62]:


# Save df to no spam
df_no_spam = df[df['CLASS'] == 0]

# 19 Legit comments with the word 'check'
df_no_spam['CONTENT'].str.contains('check').value_counts().compute()


# In[ ]:


# AWS Sage Maker is great for big data. It process similarily the same as our 
# local computer, but the difference is faster speed due to more cores and rams.
# Spark has the same idea where they use clusters. The data is broken up in partitions
# and are distributed to mutiple servers to process the execution of code then compiled
# back together when the processing finishes.
# Scale up is having a big server while scale out is having mutiple servers

