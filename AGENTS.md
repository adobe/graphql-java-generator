# GraphQL Java Generator

Ruby-based code generator that produces Java data models, query builders, and response classes from a GraphQL introspection schema JSON.

## Build

Requires Ruby >= 2.1 and Bundler.

- `bundle install` — install Ruby dependencies
- `bundle exec rake install` — install the gem locally
- `ruby example-magento-generator.rb` — generate Magento Java classes into `src/main/java/`

The `support/` module is a Gradle Java project (Java 8):
- `cd support && ./gradlew build` — build the Java support library

## Testing

- `rake test` — runs both Ruby and Java tests (generates test fixtures first)
- `cd codegen && rake test` — Ruby unit tests only (Minitest)
- `cd support && ./gradlew check` — Java unit tests only (JUnit 4)

The top-level `rake test` first runs the `:generate` task which creates `Generated.java` and `GeneratedMinimal.java` test fixtures in `support/src/test/java/`, then runs both test suites.

## Code Style

- Generated Java uses 4-space indentation (enforced by `Reformatter` in `codegen/lib/graphql_java_gen/reformatter.rb`)
- All generated Java files include the Apache 2.0 license header from `License.erb`
- Java reserved words in GraphQL field names get an automatic `Value` suffix (e.g. `class` → `classValue`)

## Module Map

| Module | Path | Description |
|--------|------|-------------|
| codegen | `codegen/lib/graphql_java_gen/` | Ruby gem: main generator, ERB templates, reformatter, scalar/annotation support |
| support | `support/src/main/java/com/shopify/graphql/support/` | Java runtime library: base query builder, response deserializer, error types |
| schemas | `schemas/` | Magento GraphQL introspection JSON files (input for generation) |
| templates | `codegen/lib/graphql_java_gen/templates/` | ERB templates for each Java entity type (Enum, Input, Interface, Object, Query, etc.) |

## Architecture

- **Generator entry point**: `GraphQLJavaGen` class in `codegen/lib/graphql_java_gen.rb` — accepts a parsed introspection schema and options (package name, custom scalars, annotations, license header)
- **Two output modes**: `save()` produces a single monolithic Java file; `save_granular()` produces one file per schema entity (preferred)
- **ERB templates**: each GraphQL kind (OBJECT, INTERFACE, UNION, INPUT_OBJECT, ENUM) maps to a template in `templates/`
- **Custom scalars**: defined via `GraphQLJavaGen::Scalar` — map GraphQL scalar types to Java types with custom deserialization expressions
- **Custom annotations**: defined via `GraphQLJavaGen::Annotation` — conditionally applied to fields (e.g. `@Nullable` for non-required fields)
- **Java support library** (`com.shopify.graphql.support`): `AbstractQuery` builds query strings, `AbstractResponse` deserializes JSON via Gson. Generated code extends these base classes.
- **Schema dependency**: uses the `graphql_schema` gem to parse introspection JSON
