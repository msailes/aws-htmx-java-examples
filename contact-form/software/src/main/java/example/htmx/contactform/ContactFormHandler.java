// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package example.htmx.contactform;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ContactFormHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Logger log = LoggerFactory.getLogger(ContactFormHandler.class);

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        log.info(event.toString());

        return new APIGatewayProxyResponseEvent()
                .withHeaders(Map.of("Content-Type", "text/html",
                        "Access-Control-Allow-Origin", "*",
                        "Access-Control-Allow-Methods", "*",
                        "Access-Control-Allow-Headers", "*"))
                .withStatusCode(200)
                .withBody("<div>Form submitted</div>");
    }
}
