# Dask vs Spark
Though there are many technical differences, these are the ones that stick in my memory.

|            | Dask                                                      | Spark                                             |   |   |
|------------|-----------------------------------------------------------|---------------------------------------------------|---|---|
| Size       | Small                                                     | Big                                               |   |   |
| Best used  | With other Python libraries                               | On its own or with Apache projects                |   |   |
| Age        | new (2014)                                                | old (2010)                                        |   |   |
| Best use   | Business intelligence operations (SQL) and lightweight ML | More general uses, including business and science |   |   |
| Ideal when | You prefer Python                                         | You prefer Scala or SQL                           |   |   |

And if you are managing terabytes of CSV or JSON data, forget about these two entirely and go work with MongoDB or PostgreSQL.