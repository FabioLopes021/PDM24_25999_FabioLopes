package com.example.students

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.navigation.NavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.students.ui.theme.StudentsTheme
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentsTheme {

            }
        }
    }
}


@Entity(tableName = "example_table")
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val Number: Int,
    val description: String,
)


@Dao
interface ExampleDao {
    @Insert
    suspend fun insert(example: ExampleEntity)

    @Delete
    suspend fun delete(example: ExampleEntity)

    @Query("SELECT * FROM example_table WHERE id = :id")
    fun getById(id: Int): Flow<ExampleEntity>
}


@Database(entities = [ExampleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase () {
    abstract fun exampleDao(): ExampleDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        @SuppressLint("RestrictedApi")
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database")
                .build()
            INSTANCE = instance
            instance
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()){
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details")
        }
        val text = createRef();

        Text(
            text = "Texto no centro",
            modifier = Modifier.constrainAs(text){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}






@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentsTheme {
        Greeting("Android")
    }
}