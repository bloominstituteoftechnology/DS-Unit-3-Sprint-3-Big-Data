*Here’s the process to view your AWS limits, and request increases if necessary. Please do this today, thanks!*

Here are some minimum limits we want:
- EC2 *m4.large* instances : *3* or more _(for a small, affordable Spark cluster)_
- SageMaker *ml.m4.4xlarge* instances : *1* or more _(for a powerful Jupyter server)_

*1.* Follow these instructions to see your *EC2 instance limits:* https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-resource-limits.html

>- Open the Amazon EC2 console at https://console.aws.amazon.com/ec2/
>- From the navigation bar, select a region. _Choose *US East (N. Virginia), US East (Ohio), or US West (Oregon)*_
>- From the navigation pane, choose *Limits.*
>- Locate the resource in the list. The Current Limit column displays the current maximum for that resource for your account.

Your limits can vary, depending on Amazon’s proprietary “trust score” which was assigned to your account when you signed up and gets updated over time.

*2.* Follow along with this video to see if you can create an *AWS SageMaker Notebook instance:* https://www.youtube.com/watch?v=YZsx2gXHCds

 _(Don’t forget to stop your instance too, so you don’t keep using credits.)_

SageMaker Instance Limits are distinct from EC2 Service Instance Limits. And the only real way to check your SageMaker Instance Limits is to try to launch SageMaker with a given Instance Type and see if you get an error or not.

If your current limits are lower than the minimums we want, proceed to the next steps:

*3.* Go to https://console.aws.amazon.com/support/cases#/create?issueType=service-limit-increase to submit a service level increase.

Limit type: *SageMaker*  
Region: *US East (N. Virginia), US East (Ohio), or US West (Oregon)*  
Resource Type: *SageMaker Notebooks*  
Limit: *ml.m4.4xlarge Instances*  
New limit value: *1*  
Use case description: I’m a student at Lambda School in the data science program. Amazon Web Services has partnered with Lambda School to provide a platform for our data engineering and machine learning lessons.  

*4.* Submit a second, separate service level increase.

Limit type: *EC2 Instances*  
Region: *US East (N. Virginia), US East (Ohio), or US West (Oregon)*  
Primary Instance Type: *m4.large*  
Limit: *Instance Limit*  
New limit value: *3*  
Use case description: (Same as above)  

_Note that just submitting the support case can increase your “trust score” and automatically increase your limits for these instances and more. After this, support staff may review the request and respond that you already have the limits you need. It’s confusing for everyone. We’ve had multiple meetings and emails with AWS but they can’t explain how it works :joy:  But that’s okay — your limits were increased and that’s all that matters!_