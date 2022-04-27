package com.example.memorygame

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.example.memorygame.R.drawable.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var frontAnim : AnimatorSet
    private lateinit var backAnim : AnimatorSet
    private var isFront = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//        val scale = applicationContext.resources.displayMetrics.density

        val images : MutableList<Int> =
            mutableListOf(
                pic1 ,
                pic2 ,
                pic3 ,
                pic4 ,
                pic5 ,
                pic6 ,
                pic1 ,
                pic2 ,
                pic3 ,
                pic4 ,
                pic5 ,
                pic6
            )

        val buttons = arrayOf(
            button1 , button2 , button3 , button4 , button5 , button6 , button7 , button8 ,
            button9 , button10 , button11 , button12
        )

//        val front = findViewById<Button>(R.id.button1)
//        val flipper = findViewById<Button>(R.id.flipperBtn)
//        val back = findViewById<Button>(buttons)

        frontAnim = AnimatorInflater.loadAnimator(
            applicationContext ,
            R.animator.front_animator
        ) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(
            applicationContext ,
            R.animator.back_animator
        ) as AnimatorSet

        val cardBack = question_mark
        var clicked = 0
        var turnOver = false
        var lastClicked = -1

        images.shuffle()
        for (i in 0..11) {
            buttons[i].setBackgroundResource(cardBack)
            buttons[i].text = "cardBack"
            buttons[i].textSize = 0.0F
            buttons[i].setOnClickListener {
                if (buttons[i].text == "cardBack" && !turnOver) {
//                    buttons[i].setBackgroundResource(images[i])
                    backAnim.setTarget(buttons[i].setBackgroundResource(images[i]))
                    frontAnim.setTarget(buttons[i].setText(images[i]))
                    if (clicked == 0) {
                        lastClicked = i
                    }
                    backAnim.start()
                    frontAnim.start()
                    clicked++
                } else if (buttons[i].text !in "cardBack") {
                    buttons[i].setBackgroundResource(cardBack)
                    backAnim.setTarget(cardBack)
                    buttons[i].text = "cardBack"
                    backAnim.start()
                    clicked--
                }

                if (clicked == 2) {
                    turnOver = true
                    if (buttons[i].text == buttons[lastClicked].text) {
                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turnOver = false
                        clicked = 0
                    }
                } else if (clicked == 0) {
                    turnOver = false
                }
            }
        }
    }
}