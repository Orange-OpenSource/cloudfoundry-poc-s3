s3-connectors-poc
===================

Show how to use the s3 connector with Cloud Foundry in spring boot. You can find this connector here [https://github.com/Orange-OpenSource/spring-cloud-s3-connectors](https://github.com/Orange-OpenSource/spring-cloud-s3-connectors).

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

 1. You just want to send a file with a known size, just use the `Normal upload` and see [https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/com/orange/clara/cloud/poc/s3/controller/PocS3Controller.java#L55-L70](https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/com/orange/clara/cloud/poc/s3/controller/PocS3Controller.java#L55-L70) in the code.
 2. You want to send a file with a unknown size, for example when you pipe to another process and you don't want to store data in a file, you just want to directly send the file to s3 storage, use the `Upload in stream`. Note that if you send a large file this method will be slightly faster.(you can see [https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/com/orange/clara/cloud/poc/s3/controller/PocS3Controller.java#L107-L120](https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/com/orange/clara/cloud/poc/s3/controller/PocS3Controller.java#L107-L120) in the code)

Deploy It !
-----------

**In all cases:**

1. Clone this repo: `git clone https://github.com/Orange-OpenSource/s3-connectors-poc.git`
2. Go inside the new created folder in command line: `cd s3-connectors-poc`
3. Build the app with maven: `mvn clean install`

### On Cloud Foundry

1. Create a s3 service from the marketplace (e.g with p-riakcs: `cf cs p-riakcs developper nameofmyservice`)
2. In command line do a `cf push`
3. After the app has been pushed bind your new created service to your app (e.g: `cf bs poc-riakcs nameofmyservice`)
4. Restage your app: `cf restage poc-riakcs`

### On heroku

1. Create and push the app on heroku
2. To use s3 connectors you have three ways
 - Use the [Bucketeer addon](https://elements.heroku.com/addons/bucketeer)
 - Use the native support of S3 on heroku: https://devcenter.heroku.com/articles/s3
 - Set an env var called `S3_URL` with the address of your s3 bucket (e.g: `S3_URL=s3://myaccesskey:mysecretaccesskey@s3.host.com/mybucketname`)
3. Restart your app: `heroku restart`

### Locally

You can use the local config connector to do this simply modify value `spring.cloud.mys3` in the file `config/spring-cloud.properties`.

Take a look on [https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/resources/spring-cloud-bootstrap.properties](https://github.com/Orange-OpenSource/s3-connectors-poc/blob/master/src/main/java/resources/spring-cloud-bootstrap.properties) to know how to set the property file location.