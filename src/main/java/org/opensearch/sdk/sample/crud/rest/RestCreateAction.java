/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.sdk.sample.crud.rest;

import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestHandler.Route;
import org.opensearch.rest.RestRequest.Method;
import org.opensearch.sdk.ExtensionRestHandler;
import org.opensearch.sdk.ExtensionRestResponse;
import org.opensearch.sdk.authz.RequiresPermissions;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.opensearch.rest.RestRequest.Method.PUT;
import static org.opensearch.rest.RestStatus.*;

/**
 * Sample REST Handler (REST Action). Extension REST handlers must implement {@link ExtensionRestHandler}.
 */
public class RestCreateAction implements ExtensionRestHandler {

    private static final String SUCCESSFUL = "PUT /create successful";

    @Override
    public List<Route> routes() {
        return singletonList(new Route(PUT, "/crud/create"));
    }

    // How should the extension list what permissions it wants to create?
    // Will the permissions be part of the API spec of the extension?
    @RequiresPermissions(
        permissions = { "extensions:sample/crud/create" }
    )
    @Override
    public ExtensionRestResponse handleRequest(Method method, String uri) {
        System.out.println("method: " + method);
        System.out.println("URI: " + uri);
        // TODO modify RestExecuteOnExtensionRequest to get request body. (Should it get params and headers too?)
        List<String> consumedParams = new ArrayList<>();
        if (Method.PUT.equals(method) && "/crud/create".equals(uri)) {
            return new ExtensionRestResponse(OK, SUCCESSFUL, consumedParams);
        }
        return new ExtensionRestResponse(
                NOT_FOUND,
                "Extension REST action improperly configured to handle " + method.name() + " " + uri,
                consumedParams
        );
    }

}
