package top.wsure.warframe.utils

import com.baomidou.mybatisplus.core.MybatisConfiguration
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder
import net.mamoe.mirai.utils.info
import org.apache.ibatis.datasource.pooled.PooledDataSource
import org.apache.ibatis.logging.stdout.StdOutImpl
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import org.sqlite.JDBC
import top.wsure.warframe.WorldState
import top.wsure.warframe.dao.UserMapper
import java.io.File
import java.io.InputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource


/**
 * Copyright (C), 上海维跃信息科技有限公司
 * FileName: SqliteUtils
 * Author:   wsure
 * Date:     2021/1/21 5:10 下午
 * Description:
 */
class SqliteUtils {
    companion object {
        fun sqliteDataSource(file: File):DataSource{
            val url = "jdbc:sqlite:${file.absolutePath}"
            val osName = System.getProperty("os.name").split(" ")[0]
            val osArch = System.getProperty("os.arch")
            WorldState.logger.info{"os.name:${osName}"}
            WorldState.logger.info{"os.arch:${osArch}"}
            WorldState.logger.info{"os.version:${System.getProperty("os.version")}"}

            val libSo = "native/${osName}/${osArch}/${getSoTypeByOs(osName)}"
            WorldState.logger.info("libSo : $libSo")
            val libFilePath = "${file.parentFile.absolutePath}/${getSoTypeByOs(osName)}"
            WorldState.logger.info("libFile : $libFilePath")
            val libFile = File(libFilePath)
            if(libFile.exists()){ libFile.delete()}
            JDBC::class.java.getResourceAsStream(libSo).toFile(libFilePath)

            System.load(libFile.absolutePath)

            val driver = "org.sqlite.JDBC"
            if(!file.exists()){
                Class.forName(driver)
                val metadata = DriverManager.getConnection(url).metaData
                WorldState.logger.info{"metaData.url:${metadata.url}"}
            }


            return PooledDataSource(driver, url, null, null)
        }

        fun sqlSessionFactory(file: File): SqlSessionFactory {
            val dataSource = sqliteDataSource(file)
            createTableIfNotExist(dataSource)
            val transactionFactory = JdbcTransactionFactory()
            val environment = Environment("Production", transactionFactory, dataSource)
            val configuration = MybatisConfiguration(environment)
            configuration.logImpl = StdOutImpl::class.java
            configuration.addMapper(UserMapper::class.java)
            return MybatisSqlSessionFactoryBuilder().build(configuration)
        }

        private fun createTableIfNotExist(dataSource: DataSource) {
            try {
                val connection: Connection = dataSource.connection
                val statement: Statement = connection.createStatement()
                statement.execute(
                    "create table if not exists user(\n" +
                            "    id BIGINT(20) primary key NOT NULL ,\n" +
                            "    nick TEXT DEFAULT NULL,\n" +
                            "    remark TEXT DEFAULT NULL,\n" +
                            "    avatar_url TEXT DEFAULT NULL,\n" +
                            "    create_date DATETIME DEFAULT NULL,\n" +
                            "    update_date DATETIME DEFAULT NULL\n" +
                            ")"
                )
            } catch (e: SQLException) {
                WorldState.logger.error(e)
            }
        }

        fun InputStream.toFile(path: String) {
            use { input ->
                File(path).outputStream().use { input.copyTo(it) }
            }
        }

        fun getSoTypeByOs(name:String):String{
            return when (name) {
                "Mac" -> "libsqlitejdbc.jnilib"
                "Windows" -> "sqlitejdbc.dll"
                else -> "libsqlitejdbc.so"
            }
        }
    }

}