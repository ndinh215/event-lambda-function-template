package com.slf.functions;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.DescribeExecutionRequest;
import com.amazonaws.services.stepfunctions.model.DescribeExecutionResult;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.logging.Logger;

@Component
public class EventQueueFunction implements Function<SQSEvent, Void> {

    private static final Logger LOG = Logger.getLogger(String.valueOf(EventQueueFunction.class));

    @Override
    public Void apply(SQSEvent event) {
        LOG.info("[INFO] " + event.toString());

        String message = event.getRecords().get(0).getBody();
        AWSStepFunctions stepFunctions = AWSStepFunctionsClientBuilder.defaultClient();

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest()
                .withStateMachineArn(System.getenv("STEP_FUNCTION_ARN"))
                .withInput(message);

        LOG.info("[INFO] " + System.getenv("STEP_FUNCTION_ARN"));

        StartExecutionResult result = stepFunctions.startExecution(stepFunctionRequest);
        LOG.info("[INFO] " + result.toString());

        DescribeExecutionRequest describeExecutionRequest = new DescribeExecutionRequest();
        describeExecutionRequest.setExecutionArn(result.getExecutionArn());

        DescribeExecutionResult describeExecutionResult = stepFunctions.describeExecution(describeExecutionRequest);
        LOG.info("[INFO] " + describeExecutionResult.toString());
        LOG.info("[INFO] " + describeExecutionResult.getStatus());

        return null;
    }
}
