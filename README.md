cloudfoundry-poc-s3
===================
Show how to use the s3 connector for Cloud Foundry in spring boot. You can find this connector here [https://github.com/Orange-OpenSource/cloudfoundry-s3-service-connector](https://github.com/Orange-OpenSource/cloudfoundry-s3-service-connector).

What's inside ?
---------------

You will found a small web app with a form to send a file.
This file will be uploaded directly in a s3 storage. 

You can after:
 - directly show this file by accessing to: `/show/my_file_name` (File will be take from s3 storage stream and showed in same time)
 - download your file by accessing to:      `/download/my_file_name` (File will be take from s3 storage stream and resend in same time to the user)
 
Multiples scenarios
-------------------

You have two scenarios inside this poc, those scenarios wants to show in the code what you can do to upload on a S3 storage:

 1. You just want to send a file with a known size, just use the `Normal upload` and see []() in the code.
 2. You want to send a file with a unknown size, for example when you pipe to another process and you don't want to store data in a file, you just want to directly send the file to s3 storage, use the `Upload in stream`. Note that if you send a large file this method will be slightly faster.(you can see []() in the code)
