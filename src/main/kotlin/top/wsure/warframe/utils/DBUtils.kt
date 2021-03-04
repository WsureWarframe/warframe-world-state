package top.wsure.warframe.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import top.wsure.warframe.WorldState
import top.wsure.warframe.entity.UserTable
import java.io.File


/**

 * FileName: SqliteUtils
 * Author:   wsure
 * Date:     2021/1/21 5:10 下午
 * Description:
 */
class DBUtils {
    companion object {
        private const val username: String = "Wsure"
        private const val password: String = "Wsure"
        private const val driver = "org.h2.Driver"

        fun getDatabase(file: File): Database {
            val url = "jdbc:h2:file:${file.absolutePath};AUTO_SERVER=TRUE"
            return Database.connect(
                url = url,
                driver = driver,
                user = username,
                password = password
            )
        }

        fun initTableIfNotExist() {

            val createTablesSql = "create table if not exists user(\n" +
                    "    id BIGINT(20) primary key NOT NULL ,\n" +
                    "    nick TEXT DEFAULT NULL,\n" +
                    "    remark TEXT DEFAULT NULL,\n" +
                    "    avatar_url TEXT DEFAULT NULL,\n" +
                    "    create_date DATETIME DEFAULT NULL,\n" +
                    "    update_date DATETIME DEFAULT NULL\n" +
                    ");\n" +
                    "create table if not exists qq_group(\n" +
                    "    id BIGINT(20) primary key NOT NULL ,\n" +
                    "    name TEXT DEFAULT NULL,\n" +
                    "    remark TEXT DEFAULT NULL,\n" +
                    "    avatar_url TEXT DEFAULT NULL,\n" +
                    "    create_date DATETIME DEFAULT NULL,\n" +
                    "    update_date DATETIME DEFAULT NULL\n" +
                    ");\n" +
                    "create table if not exists search_record(\n" +
                    "    id BIGINT(20) primary key auto_increment NOT NULL ,\n" +
                    "    user_id BIGINT(20) NOT NULL ,\n" +
                    "    group_id BIGINT(20) DEFAULT NULL ,\n" +
                    "    nick TEXT DEFAULT NULL,\n" +
                    "    key_word TEXT DEFAULT NULL,\n" +
                    "    param TEXT DEFAULT NULL,\n" +
                    "    url TEXT DEFAULT NULL,\n" +
                    "    create_date DATETIME DEFAULT NULL,\n" +
                    "    update_date DATETIME DEFAULT NULL\n" +
                    ");"
            transaction(getDatabase(WorldState.DB_FILE)) {
                SchemaUtils.create(UserTable)
                addLogger(StdOutSqlLogger)
                exec(createTablesSql)
            }
        }

    }

}