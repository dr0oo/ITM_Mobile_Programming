package com.example.itm_term_project

class Metronome {

    val met_thread : Thread = Thread()

    fun startMetronome(){
        Thread(Runnable {
            for(i in 1..8 step(1)) {
                for(j in 1..4) {
                    println("${i} : 마디, ${j} : 박자")
                    //1초간 sleep (대기) 설정
                    Thread.sleep(600)
                }
//            sc.stopRecording()
            }
        })
    }

    fun finishMetronome(){

    }
}