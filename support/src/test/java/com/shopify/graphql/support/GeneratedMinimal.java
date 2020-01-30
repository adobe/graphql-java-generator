/*******************************************************************************
 *
 *    Copyright 2020 Adobe. All rights reserved.
 *    This file is licensed to you under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License. You may obtain a copy
 *    of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under
 *    the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 *    OF ANY KIND, either express or implied. See the License for the specific language
 *    governing permissions and limitations under the License.
 *
 ******************************************************************************/

package com.shopify.graphql.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Arguments;
import com.shopify.graphql.support.Error;
import com.shopify.graphql.support.AbstractQuery;
import com.shopify.graphql.support.SchemaViolationError;
import com.shopify.graphql.support.TopLevelResponse;
import com.shopify.graphql.support.Input;

import com.shopify.graphql.support.ID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneratedMinimal {
    public static QueryRootQuery query(QueryRootQueryDefinition queryDef) {
        StringBuilder queryString = new StringBuilder("{");
        QueryRootQuery query = new QueryRootQuery(queryString);
        queryDef.define(query);
        queryString.append('}');
        return query;
    }

    public static class QueryResponse {
        private TopLevelResponse response;
        private QueryRoot data;

        public QueryResponse(TopLevelResponse response) throws SchemaViolationError {
            this.response = response;
            this.data = response.getData() != null ? new QueryRoot(response.getData()) : null;
        }

        public QueryRoot getData() {
            return data;
        }

        public List<Error> getErrors() {
            return response.getErrors();
        }

        public String toJson() {
            return new Gson().toJson(response);
        }

        public String prettyPrintJson() {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(response);
        }

        public static QueryResponse fromJson(String json) throws SchemaViolationError {
            final TopLevelResponse response = new Gson().fromJson(json, TopLevelResponse.class);
            return new QueryResponse(response);
        }
    }

    public interface QueryRootQueryDefinition {
        void define(QueryRootQuery _queryBuilder);
    }

    public static class QueryRootQuery extends AbstractQuery<QueryRootQuery> {
        QueryRootQuery(StringBuilder _queryBuilder) {
            super(_queryBuilder);
        }

        public QueryRootQuery version() {
            startField("version");

            return this;
        }

        public String toString() {
            return _queryBuilder.toString();
        }
    }

    public static class QueryRoot extends AbstractResponse<QueryRoot> {
        public QueryRoot() {
        }

        public QueryRoot(JsonObject fields) throws SchemaViolationError {
            for (Map.Entry<String, JsonElement> field : fields.entrySet()) {
                String key = field.getKey();
                String fieldName = getFieldName(key);
                switch (fieldName) {
                    case "version": {
                        String optional1 = null;
                        if (!field.getValue().isJsonNull()) {
                            optional1 = jsonAsString(field.getValue(), key);
                        }

                        responseData.put(key, optional1);

                        break;
                    }

                    case "__typename": {
                        responseData.put(key, jsonAsString(field.getValue(), key));
                        break;
                    }
                    default: {
                        throw new SchemaViolationError(this, key, field.getValue());
                    }
                }
            }
        }

        public String getGraphQlTypeName() {
            return "QueryRoot";
        }

        public String getVersion() {
            return (String) get("version");
        }

        public QueryRoot setVersion(String arg) {
            optimisticData.put(getKey("version"), arg);
            return this;
        }

        public boolean unwrapsToObject(String key) {
            switch (getFieldName(key)) {
                case "version": return false;

                default: return false;
            }
        }
    }
}
