# aws-htmx-java-examples


## Contact Form Example

### Requires

maven
cdk

### Setup

Build the Lambda function

```bash
cd contact-form/software
mvn clean package
```

Deploy the API Gateway and Lambda function

```bash
cd ../infrastructure
cdk deploy
```

Edit the HTML of ```www/index.html``` to include your API Gateway URL

### Test

Load the HTML page from www and click submit, you should see the contents of the form delivered to your Lambda function.