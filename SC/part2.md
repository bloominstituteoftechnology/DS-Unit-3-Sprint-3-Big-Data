# Databricks

Databricks seems well-suited to **prototyping and sketching a distributed computing strategy**. It gives me a low-pressure environment to get comfortable with dispatching jobs to clusters with spark, simply by *simulating* a distributed environment in a free-to-play service. For example, if I'm starting a brand new ETL pipeline that will ultimately perform well when dumped in the order of 10e40 rows and 10e60 features, I'd want to start in Databricks' free-to-use mode to make sure I get my tools playing nicely with my samples.

# deploying

Once my team is *oriented to the problem* and have reasonably well-iterated ETL code, we'd port it to whichever cloud service has the best prices at the time, provided all else equal with smooth notebook environments and library compatibility. Here we might end up on AWS, Azure, or even Databricks' paid service. 

# orders of magnitude 

### small data: 
If the data dump is within the order of maybe 10e9 observations and 10e6 features, I'd handle it on a personal computer. I may be required to sample down a few orders during notebook work sessions, and I'd have to be very clever sometimes, but it'd be completely doable. 

### medium data: 
For a data dump in the order of 10e10-10e20 observations and 10e7-10-12 features, I think it'd be fun to make my own personal cloud by asking family and friends if I can run docker packages on their computers! In a professional environment, this could mean some grouping of PCs that are lying around the office.  

### big data: 
Anything bigger and we're definitely gonna be a big cloud customer. 

![orders.jpg](decisionmaking)
