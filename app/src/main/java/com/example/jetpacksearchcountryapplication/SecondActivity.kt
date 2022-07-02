package com.example.jetpacksearchcountryapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpacksearchcountryapplication.ui.theme.JetpackSearchCountryApplicationTheme
import com.example.jetpacksearchcountryapplication.ui.theme.Purple200
import com.example.jetpacksearchcountryapplication.ui.theme.Purple500
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackSearchCountryApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Scaffold(
                        topBar = { TopBarCustome() },
                        backgroundColor = MaterialTheme.colors.background
                    ) {

                        ListNavigation()
                    }
                }
            }
        }
    }

}

@Composable
fun TopBarCustome() {

    TopAppBar(
        title = { "Search in static list" },
        backgroundColor = Purple500,
        contentColor = Color.White
    )
}

@Composable
fun ListNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "MyList"
    ) {
        composable("MyList") {
            MyListScreen(navController)
        }
    }
}

@Composable
fun MyListScreen(navController: NavHostController) {

    var textVal = remember { mutableStateOf(TextFieldValue("")) }

    Column {

        SearchTextField(textVal)
        MyList(textVal)
    }

}


@Composable
fun SearchTextField(textVal: MutableState<TextFieldValue>) {

    TextField(
        value = textVal.value, onValueChange = {
            textVal.value = it
        },

        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
//            if(textVal.value != TextFieldValue("")){
            if (textVal.value.text != "") {  //
                IconButton(onClick = {
                    textVal.value = TextFieldValue("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },

        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Purple200,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )

    )

}


@Composable
fun MyList(textVal: MutableState<TextFieldValue>) {


    val context = LocalContext.current

    val namesList = getNamesList()
    var filteredNames: ArrayList<String>

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        val searchText = textVal.value.text

        filteredNames =
            if (searchText.isEmpty()) {
                namesList
            } else {

                val resultList = ArrayList<String>()
                for (name in namesList) {
                    if (name.lowercase(Locale.getDefault())
                            .contains(searchText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(name)
                    }
                }
                resultList
            }

        items(items = filteredNames, itemContent = { item ->

            NameListItem(
                nameText = item,
                onItemClicked = { selectedName ->
                    Toast.makeText(context, selectedName, Toast.LENGTH_SHORT).show()
                }
            )
        },
        )


    }

}

@Composable
fun NameListItem(
    nameText: String,
    onItemClicked: (String) -> Unit
) {

    Row(modifier = Modifier
        .clickable {
            onItemClicked(nameText)
        }
        .padding(5.dp)
        .fillMaxWidth()
        .background(Purple500)
        .fillMaxWidth()
        .height(60.dp)
        .padding(5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = nameText, fontSize = 18.sp)
    }
}

@Composable
fun getNamesList(): ArrayList<String> {

    val namesList = ArrayList<String>()
    namesList.add("ali rezai")
    namesList.add("milad hassani")
    namesList.add("hassan mortazavi")
    namesList.add("shiva sattari")
    namesList.add("mina madadai")
    return namesList
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackSearchCountryApplicationTheme {
        ListNavigation()
    }
}