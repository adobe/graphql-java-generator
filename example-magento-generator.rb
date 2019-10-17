require 'graphql_java_gen'
require 'graphql_schema'
require 'json'

introspection_result = File.read("schemas/magento-schema-2.3.3.json")
schema = GraphQLSchema.new(JSON.parse(introspection_result))

GraphQLJavaGen.new(schema,
  package_name: "com.adobe.cq.commerce.magento.graphql",
  license_header_file: "./License.erb",
  nest_under: 'Schema', # Not used, but must be defined
  custom_scalars: [
    GraphQLJavaGen::Scalar.new(
      type_name: 'Decimal',
      java_type: 'BigDecimal',
      deserialize_expr: ->(expr) { "new BigDecimal(jsonAsString(#{expr}, key))" },
      imports: ['java.math.BigDecimal'],
    ),
  ]
).save_granular("#{Dir.pwd}/src/main/java/com/adobe/cq/commerce/magento/graphql")
