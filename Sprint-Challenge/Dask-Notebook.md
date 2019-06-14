

```python
import dask.dataframe as dd
from dask import compute
```


```python
df = dd.read_csv('../../Youtube*.csv')
```


```python
compute(df.columns)
```




    (Index(['COMMENT_ID', 'AUTHOR', 'DATE', 'CONTENT', 'CLASS'], dtype='object'),)




```python
compute(df.shape)
```




    ((1956, 5),)




```python
compute(df['CLASS'].value_counts())
```




    (1    1005
     0     951
     Name: CLASS, dtype: int64,)




```python
df['CONTENT'] = df['CONTENT'].str.lower()
```


```python
contains_check = df[df['CONTENT'].str.contains('check')]
```


```python
compute(contains_check['CLASS'].value_counts())
```




    (1    461
     0     19
     Name: CLASS, dtype: int64,)


