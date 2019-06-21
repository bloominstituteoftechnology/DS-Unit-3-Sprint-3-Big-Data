#!/usr/bin/env python
# coding: utf-8

# In[4]:


import pandas as pd


# In[14]:


import dask.dataframe as dd


# In[12]:


get_ipython().run_line_magic('ls', '')


# In[15]:


Youtube = dd.read_csv('*.csv')


# In[16]:


Youtube.head()


# In[28]:


Youtube[Youtube['CLASS'] ==1]


# In[41]:


Youtube[Youtube.CLASS == 1].compute()


# In[64]:


Youtube_1 = Youtube[Youtube.CLASS == 0].compute()


# In[61]:


Youtube[Youtube.AUTHOR == 'BOB'].compute()


# In[66]:


Youtube[Youtube.resource_record!='AAAA'].resource_record.value_counts().compute()


# In[68]:


Youtube[Youtube.CLASS == 1].resource_record.value_counts().compute()


# In[74]:


Youtube_0 = Youtube[Youtube.CLASS == 0]


# In[75]:


Youtube_0


# In[76]:


Youtube[Youtube.CLASS == 1].CLASS.value_counts().compute()


# In[77]:


Youtube[Youtube.CLASS == 0].CLASS.value_counts().compute()


# In[84]:


Youtube['CLASS'].plot(kind='bar').compute()


# In[80]:


Psy = pd.read_csv('Youtube01-Psy.csv')


# In[81]:


Psy.head()


# In[89]:


get_ipython().run_line_magic('time', '')
Psy_graph = pd.DataFrame(Psy['CLASS'])


# In[98]:


Psy.reset_index()


# In[100]:


import matplotlib.pyplot as plt
plt.scatter(Psy.index,Psy.CLASS)


# In[105]:


Youtube[Youtube.CONTENT == ''].CONTENT.value_counts().compute()


# In[104]:


Youtube.head(1900)


# In[109]:





# In[116]:


Psy['DATE'] =  pd.to_datetime(Psy['DATE'], format='%Y%M%D:%H:%M:%S')


# In[114]:


Psy.head()


# In[117]:


Psy[['DATE']] = Psy[['DATE']].apply(pd.to_datetime)


# In[119]:


Psy.dtypes


# In[122]:


Psy['DATE'].plot();


# In[ ]:




