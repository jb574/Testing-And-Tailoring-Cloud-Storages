package sample
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table



/**
 * Created by jackdavey on 28/07/15.
 */
object DynamoDBInterface
{
   val awsCreds = new BasicAWSCredentials("AKIAJ3Z34NX2YHNHFOXA", "ZcwXYRMtkcJGNqDnnnmnuzAslLtBAgpo3bIF3rkA")
   val dynamoDB =   new DynamoDB(new AmazonDynamoDBClient(awsCreds))

  def preformRead():Int =
  {
    val table = dynamoDB.getTable("test")
    val item = table.getItem("name","dad")
    item.get("age").asInstanceOf[Int]
  }

  /**
  def preformWrite() =
  {
    val table = dynamoDB.getTable("test")
    table.updateItem()
  }

  **/
}
