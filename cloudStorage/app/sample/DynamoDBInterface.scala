package sample
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.{ValueMap, NameMap}
import com.amazonaws.services.dynamodbv2.model._
import scala.collection.JavaConverters
import scala.util.Random

/**
 * Created by jackdavey on 28/07/15.
 */
class DynamoDBInterface  extends  DatabaseConnector
{
   val awsCreds = new BasicAWSCredentials("AKIAJYJ4PYBBM3TKXXPQ", "6y83XxoA8ezANpPNvazTbDCaCJH4G66qyNa5v7BO")
   val dynamoDB =   new DynamoDB(new AmazonDynamoDBClient(awsCreds))

  def OldMethod: TableDescription =
  {
    var myList: List[AttributeDefinition] = List()
    var attr = new AttributeDefinition().withAttributeName("id").withAttributeType("N")
    myList = attr :: myList
    var schema: List[KeySchemaElement] = List()
    var actualSchema = new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH)
    schema = actualSchema :: schema
    val properSchema = JavaConverters.asJavaCollectionConverter(schema)
    val attrFinl = JavaConverters.asJavaCollectionConverter(myList)

    val request = new CreateTableRequest()
      .withTableName("main")
      .withKeySchema(properSchema.asJavaCollection)
      .withAttributeDefinitions(attrFinl.asJavaCollection)
      .withProvisionedThroughput(new ProvisionedThroughput()
      .withReadCapacityUnits(5L)
      .withWriteCapacityUnits(6L))
    val table = dynamoDB.createTable(request)
    table.waitForActive()
  }


  def read():Int =
  {
    val table = dynamoDB.getTable("main")
    val item = table.getItem("id",101)
    val result=  item.getNumber("age").intValue()
    result
  }


  def write(age:Int) =
  {
    val table = dynamoDB.getTable("main")
     createItem(table,age)
  }


  def createItem(table: Table,age:Int) =
  {
    val item = new Item().withPrimaryKey("id", 101)
      .withString("name", "dad")
      .withNumber("age", age)
    table.putItem(item)
  }
}
