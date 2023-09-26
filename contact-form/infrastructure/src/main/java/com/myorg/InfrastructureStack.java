package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.amazon.awscdk.services.apigateway.Cors;
import software.amazon.awscdk.services.apigateway.CorsOptions;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Alias;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SnapStartConf;
import software.amazon.awscdk.services.lambda.Tracing;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureStack extends Stack {
    public InfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        var contactFormFunction = Function.Builder.create(this, "htmx-form-handler")
                .memorySize(2048)
                .runtime(Runtime.JAVA_17)
                .handler("example.htmx.contactform.ContactFormHandler")
                .code(Code.fromAsset("../software/target/aws-htmx-java-lambda-1.0-SNAPSHOT.jar"))
                .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
                .tracing(Tracing.ACTIVE)
                .build();

        var version = contactFormFunction.getCurrentVersion();
        var alias = Alias.Builder.create(this, "htmx-form-handler-alias")
                .aliasName("prod")
                .version(version)
                .build();

        List<String> allHeaders = new ArrayList<>();
        allHeaders.add("*");

        LambdaRestApi restApi = LambdaRestApi.Builder.create(this, "htmx-form-api")
                .restApiName("htmx-form-api")
                .handler(alias)
                .defaultCorsPreflightOptions(CorsOptions.builder()
                        .allowOrigins(Cors.ALL_ORIGINS)
                        .allowMethods(Cors.ALL_METHODS)
                        .allowHeaders(allHeaders)
                        .build())
                .build();

        new CfnOutput(this, "api-url", CfnOutputProps.builder()
                .value(restApi.getUrl())
                .build());
    }
}
