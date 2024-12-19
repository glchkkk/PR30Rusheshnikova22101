package com.example.pr30rusheshnikova22101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerUpApp()
        }
    }
}

@Composable
fun PowerUpApp() {
    var showSplashScreen by remember { mutableStateOf(true) }

    if (showSplashScreen) {
        SplashScreen { showSplashScreen = false }
    } else {
        FitnessApp()
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({ onTimeout() }, 2000)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("PowerUp", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logo30),
                contentDescription = "App Icon",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}


@Composable
fun FitnessApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "categories") {
        composable("categories") { CategoriesScreen(navController) }
        composable("press") { PressScreen(navController) }
        composable("legs") { LegsScreen(navController) }
        composable("arms") { ArmsScreen(navController) }
        composable("calendar") { CalendarScreen(navController) }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var workoutDone by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            IconButton(onClick = { navController.navigate("back") }) {
                Icon(painter = painterResource(id = R.drawable.back), contentDescription = "back")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        HorizontalPager(count = 1, state = pagerState) { page ->
            CalendarView(selectedDate, onDateSelected = { selectedDate = it }, workoutDone)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Checkbox(checked = workoutDone, onCheckedChange = { workoutDone = it })
    Text("Тренировка проведена")
}


@Composable
fun PressScreen(navController: NavController) { // navController получен как параметр
    WorkoutScreen("Упражнения на пресс", "1. Лягте на живот, затем поднимитесь на локти и носки ног, создавая прямую линию от головы до пяток.\n" +
            "2. Держите тело в напряжении, стараясь не прогибать спину.\n" +
            "3. Удерживайте позицию 20-30 секунд (или больше, если сможете).\n", navController)
}

@Composable
fun LegsScreen(navController: NavController) {
    WorkoutScreen("Упражнения на руки", "1. Примите положение лежа на животе, положив руки на ширине плеч.\n" + "2. На вдохе опустите тело вниз, сгибая руки в локтях.\n" + "3. На выдохе поднимите тело обратно в исходное положение.", navController)
}

@Composable
fun ArmsScreen(navController: NavController) {
    WorkoutScreen("Упражнения на ноги", "1. Встаньте прямо, ноги на ширине плеч, носки чуть развернуты наружу.\n" + "2. На вдохе опустите бедра вниз, как будто садитесь на стул, сгибая колени.\n" + "3. На выдохе поднимитесь обратно в исходное положение.", navController)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoriesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Категории тренировок", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("press") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
        ) {
            Text("Упражнения на пресс")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("legs") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
        ) {
            Text("Упражнения на руки")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("arms") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
        ) {
            Text("Упражнения на ноги")
        }
        Spacer(modifier = Modifier.height(32.dp))
        IconButton(onClick = { navController.navigate("calendar") }) {
            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = "Calendar")
        }
    }
}


@Composable
fun CalendarView(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit, workoutDone: Boolean) {
    val dates = generateDates(selectedDate.minusDays(10), selectedDate.plusDays(10)) // Пример генерации дат

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(dates) { date ->
            CalendarDayItem(
                date,
                selectedDate,
                onDateSelected,
                workoutDone
            )
        }
    }
}
@Composable
fun CalendarDayItem(date: LocalDate, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit, workoutDone: Boolean){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)){
        Text(
            text = date.dayOfMonth.toString(),
            modifier = Modifier.weight(1f)
        )
        Checkbox(checked = workoutDone, onCheckedChange = {  })
    }
}

@Composable
fun generateDates(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var currentDate = startDate
    while (currentDate <= endDate) {
        dates.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }
    return dates
}



@Composable
fun WorkoutScreen(title: String, workoutDescription: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("back") }) {
                Icon(painter = painterResource(id = R.drawable.back), contentDescription = "back")
            }
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = workoutDescription,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(top = 24.dp),
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal
        )
    }
}