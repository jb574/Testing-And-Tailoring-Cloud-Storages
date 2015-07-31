package sample
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model._
import scala.collection.JavaConverters
import scala.util.Random

/**
 * Created by jackdavey on 28/07/15.
 */
object DynamoDBInterface
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


  def preformRead():Int =
  {
    preformWrite()
    val table = dynamoDB.getTable("main")
    val item = table.getItem("id",101)
    item.getNumber("age").intValue()
  }


  def preformWrite() =
  {
    val table = dynamoDB.getTable("main")
    createItem(table,new Random().nextInt(50))
  }


  def createItem(table: Table,age:Int) =
  {
    val item = new Item().withPrimaryKey("id", 101)
      .withString("name", "dad")
      .withNumber("age", age)
    table.putItem(item)
  }
}
