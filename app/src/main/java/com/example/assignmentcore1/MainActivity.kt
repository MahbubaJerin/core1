package com.example.assignmentcore1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.assignmentcore1.ui.theme.AssignmentCore1Theme

class MainActivity : ComponentActivity() {

 companion object{
      const val scoreval = "scoreval"
     const val diceval = "diceval"
     const val rollButtonActivation = "rollButtonActivation"
 }

    var score = 0
    var diceValue = 0
    var isRollButtonActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.layout2)
            Log.d("Portrait mode", "This app is in Portrait Mode")
        } else {
            setContentView(R.layout.layout3)
            Log.d("Horizontal mode", "Horizontal Mode")
        }

        val scoreView: TextView = findViewById(R.id.ScoreView)
        val rollButton: Button = findViewById(R.id.RollButton)
        val addButton: Button = findViewById(R.id.AddButton)
        val subtractButton: Button = findViewById(R.id.SubsButton)
        val resetButton: Button = findViewById(R.id.ResetButton)
        val diceImage: ImageView = findViewById(R.id.dicepic)


        resetButton.setOnClickListener {
            score = 0
            diceValue = 0
            diceImage.setImageResource(R.drawable.dice_6)
            changeScore(scoreView, score)
        }

        rollButton.setOnClickListener {
            if (isRollButtonActive) {
                diceValue = rollDice()
                when (diceValue) {
                    1 -> diceImage.setImageResource(R.drawable.dice_1)
                    2 -> diceImage.setImageResource(R.drawable.dice_2)
                    3 -> diceImage.setImageResource(R.drawable.dice_3)
                    4 -> diceImage.setImageResource(R.drawable.dice_4)
                    5 -> diceImage.setImageResource(R.drawable.dice_5)
                    else -> diceImage.setImageResource(R.drawable.dice_6)
                }
                isRollButtonActive = false
            }
        }

        addButton.setOnClickListener {
            score += diceValue
            changeScore(scoreView, score)
            diceValue = 0
            isRollButtonActive = true
        }

        subtractButton.setOnClickListener {
            score = maxOf(0, score - diceValue)
            changeScore(scoreView, score)
            diceValue = 0
            isRollButtonActive = true
        }
    }
    fun changeScore(scoreView: TextView, value: Int) {
        if (value < 19) {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else if (value > 20) {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.green))
            val message = getString(R.string.congratulations_message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        scoreView.text = value.toString()
    }

    fun rollDice(): Int {
        return (1..6).random()
    }

    override fun onSaveInstanceState(outState : Bundle){
        super.onSaveInstanceState(outState)
        outState.putInt(scoreval,score)
        outState.putInt(diceval, diceValue)
        outState.putBoolean(rollButtonActivation, isRollButtonActive)
        Log.i("Onsave", score.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle){
        super.onRestoreInstanceState(savedInstanceState)
        score= savedInstanceState.getInt(scoreval)
        changeScore(findViewById(R.id.ScoreView),score)
        diceValue = savedInstanceState.getInt(diceval)
        isRollButtonActive= savedInstanceState.getBoolean(rollButtonActivation)

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
    AssignmentCore1Theme {
        Greeting("User")
    }
}
}
