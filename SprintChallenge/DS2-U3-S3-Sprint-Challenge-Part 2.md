## Data Science Unit 3 Sprint Challenge 3 — Big Data


# Part 2. Big data options
You've been introduced to a variety of platforms (AWS SageMaker, AWS EMR, Databricks), libraries (Numba, Dask, MapReduce, Spark), and languages (Python, SQL, Scala, Java) that can "scale up" or "scale out" for faster processing of big data.

Write a paragraph comparing some of these technology options. For example, you could describe which technology you may personally prefer to use, in what circumstances, for what reasons.

The first concept to keep in mind is KISS - Keep It Simple, Stupid. Can I accomplish everything I need to in 
my local environment without having to spend money and resources to upgrade. No need, as a former prof once
told me, to use a cannon to kill a fly. 
When I start to exceed the computational resources my local machine, then I woud consider going to AWS Sagemaker.
I found that for my WaterPump project (Unit 2, Sprint5), running the RandomForestClassifier with the njobs
parameter set to -1 (all available processors) decreased running time from 232 to 53 seconds, a performance
gain of  almost 80%!
In those instances where data is too big to fit into memory, I would consider using Spark/Scala, and let
Distributed Dask handle performance and optimization.

