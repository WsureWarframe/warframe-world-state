package top.wsure.warframe.utils

import org.ktorm.database.Database
import java.io.File
import java.sql.Connection
import java.sql.Statement


/**

 * FileName: SqliteUtils
 * Author:   wsure
 * Date:     2021/1/21 5:10 下午
 * Description:
 */
class DBUtils {
    companion object {
        private const val username:String = "Wsure"
        private const val password:String = "Wsure"
        private const val driver = "org.h2.Driver"

        fun getDatabase(file: File):Database{
            val url = "jdbc:h2:file:${file.absolutePath};AUTO_SERVER=TRUE"
            return Database.connect(url, driver, username, password)
        }

        fun initTableIfNotExist(database: Database) {

            database.useConnection { conn ->
                val createUserSql = "create table if not exists user(\n" +
                        "    id BIGINT(20) primary key NOT NULL ,\n" +
                        "    nick TEXT DEFAULT NULL,\n" +
                        "    remark TEXT DEFAULT NULL,\n" +
                        "    avatar_url TEXT DEFAULT NULL,\n" +
                        "    create_date DATETIME DEFAULT NULL,\n" +
                        "    update_date DATETIME DEFAULT NULL\n" +
                        ")"

                val createGroupSql = "create table if not exists user(\n" +
                        "    id BIGINT(20) primary key NOT NULL ,\n" +
                        "    name TEXT DEFAULT NULL,\n" +
                        "    remark TEXT DEFAULT NULL,\n" +
                        "    avatar_url TEXT DEFAULT NULL,\n" +
                        "    create_date DATETIME DEFAULT NULL,\n" +
                        "    update_date DATETIME DEFAULT NULL\n" +
                        ")"
                conn.prepareStatement(createUserSql).execute()
            }
        }

    }

}