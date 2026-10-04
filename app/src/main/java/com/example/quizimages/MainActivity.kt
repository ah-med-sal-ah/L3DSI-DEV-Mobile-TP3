package com.example.quizimages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import android.widget.ProgressBar
data class Question(
    val texte: String,
    val image: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val descriptionImage: String
)

class MainActivity : AppCompatActivity() {

    private val questions = arrayOf(

        Question(
            texte = "Quelle forme géométrique voyez-vous ?",
            image = R.drawable.triangle,
            choix = arrayOf("Triangle", "Cercle", "Carré"),
            bonneReponse = 0,
            descriptionImage = "Un triangle"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous ?",
            image = R.drawable.carre,
            choix = arrayOf("Rectangle", "Carré", "Cercle"),
            bonneReponse = 1,
            descriptionImage = "Un carré"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous ?",
            image = R.drawable.cercle,
            choix = arrayOf("Étoile", "Triangle", "Cercle"),
            bonneReponse = 2,
            descriptionImage = "Un cercle"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous ?",
            image = R.drawable.etoile,
            choix = arrayOf("Étoile", "Carré", "Rectangle"),
            bonneReponse = 0,
            descriptionImage = "Une étoile"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous ?",
            image = R.drawable.rectangle,
            choix = arrayOf("Cercle", "Triangle", "Rectangle"),
            bonneReponse = 2,
            descriptionImage = "Un rectangle"
        )
    )
    private var questionActuelle = 0
    private var score = 0
    private var reponseDonnee = false
    private var progression = 0

    private fun afficherQuestion() {
        val question = questions[questionActuelle]
        findViewById<TextView>(R.id.txt_numero).text =
            "Question ${questionActuelle + 1} sur ${questions.size}"
        findViewById<ImageView>(R.id.img_question)
            .setImageResource(question.image)
        findViewById<ImageView>(R.id.img_question).contentDescription =
            question.descriptionImage
        findViewById<TextView>(R.id.txt_question).text =
            question.texte

        findViewById<Button>(R.id.btn_choix1).text =
            question.choix[0]

        findViewById<Button>(R.id.btn_choix2).text =
            question.choix[1]

        findViewById<Button>(R.id.btn_choix3).text =
            question.choix[2]

        findViewById<TextView>(R.id.txt_score).text =
            "Score : $score/5"

        findViewById<ProgressBar>(R.id.progress_quiz).progress =
            progression

        reponseDonnee = false

        findViewById<Button>(R.id.btn_choix1).isEnabled = true
        findViewById<Button>(R.id.btn_choix2).isEnabled = true
        findViewById<Button>(R.id.btn_choix3).isEnabled = true

        findViewById<TextView>(R.id.txt_feedback).text = ""

        findViewById<Button>(R.id.btn_suivant).visibility =
            android.view.View.GONE
    }
    private fun verifierReponse(indexChoix: Int) {

        if (reponseDonnee) {
            return
        }

        val question = questions[questionActuelle]

        if (indexChoix == question.bonneReponse) {
            score++
            findViewById<TextView>(R.id.txt_feedback).text =
                "Bonne réponse !"
        } else {
            findViewById<TextView>(R.id.txt_feedback).text =
                "Mauvaise réponse !"
        }

        reponseDonnee = true
        progression++
        findViewById<Button>(R.id.btn_choix1).isEnabled = false
        findViewById<Button>(R.id.btn_choix2).isEnabled = false
        findViewById<Button>(R.id.btn_choix3).isEnabled = false

        findViewById<TextView>(R.id.txt_score).text =
            "Score : $score/5"

        findViewById<ProgressBar>(R.id.progress_quiz).progress =
            progression
        val boutonSuivant = findViewById<Button>(R.id.btn_suivant)

        if (questionActuelle == questions.size - 1) {
            boutonSuivant.text = "Voir le résultat"
        } else {
            boutonSuivant.text = "Question suivante"
        }

        boutonSuivant.visibility = android.view.View.VISIBLE
    }
    private fun afficherResultat() {

        findViewById<TextView>(R.id.txt_resultat).text =
            "Résultat final : $score/5"

        findViewById<TextView>(R.id.txt_resultat).visibility =
            android.view.View.VISIBLE

        findViewById<Button>(R.id.btn_choix1).visibility =
            android.view.View.GONE

        findViewById<Button>(R.id.btn_choix2).visibility =
            android.view.View.GONE

        findViewById<Button>(R.id.btn_choix3).visibility =
            android.view.View.GONE

        findViewById<Button>(R.id.btn_suivant).visibility =
            android.view.View.GONE

        findViewById<Button>(R.id.btn_rejouer).visibility =
            android.view.View.VISIBLE
    }
    private fun rejouerQuiz() {

        questionActuelle = 0
        score = 0
        progression = 0
        reponseDonnee = false

        findViewById<Button>(R.id.btn_choix1).visibility =
            android.view.View.VISIBLE

        findViewById<Button>(R.id.btn_choix2).visibility =
            android.view.View.VISIBLE

        findViewById<Button>(R.id.btn_choix3).visibility =
            android.view.View.VISIBLE

        findViewById<Button>(R.id.btn_rejouer).visibility =
            android.view.View.GONE

        findViewById<TextView>(R.id.txt_resultat).visibility =
            android.view.View.GONE

        afficherQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        afficherQuestion()
        findViewById<Button>(R.id.btn_choix1).setOnClickListener {
            verifierReponse(0)
        }

        findViewById<Button>(R.id.btn_choix2).setOnClickListener {
            verifierReponse(1)
        }

        findViewById<Button>(R.id.btn_choix3).setOnClickListener {
            verifierReponse(2)
        }
        findViewById<Button>(R.id.btn_suivant).setOnClickListener {

            if (questionActuelle == questions.size - 1) {
                afficherResultat()
            } else {
                questionActuelle++
                afficherQuestion()
            }
        }
        findViewById<Button>(R.id.btn_rejouer).setOnClickListener {
            rejouerQuiz()
        }
    }
}