package sample

import scala.util.Random

/**
 * Created by jackdavey on 10/08/15.
 */
object BasicAavailabilitySampleOne
{
    def runSample(db:DatabaseConnector): Unit =
    {
      var count = 0
      while (count < 10)
      {
        db.write(new Random().nextInt(100))
        count  += 1
        Thread.sleep(10000)
      }
    }
}
