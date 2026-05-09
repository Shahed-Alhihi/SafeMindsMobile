//package com.example.safemindsmobile.data.validation
//
//import com.example.safemindsmobile.data.model.SessionData
//
//class SessionValidator {
//    fun isValid(session: SessionData): Boolean{
//        if (session.dataId.isBlank()){
//            return false
//        }
//        if (session.timestamp <=0){
//            return false
//        }
//        if (session.userId.isBlank()){
//            return false
//        }
//        if (session.summary.totalEpochs <=0){
//            return false
//        }
//        if (session.summary.movementMean < 0){
//            return false
//        }
//        return true
//
//    }
//}

package com.example.safemindsmobile.data.validation

import com.example.safemindsmobile.data.model.SessionData

class SessionValidator {

    fun isValid(session: SessionData): Boolean {
        if (session.sessionId.isBlank()) {
            return false
        }

        if (session.sessionEnd <= 0) {
            return false
        }

        if (session.userId.isNullOrBlank()) {
            return false
        }

        if (session.summary.totalEpochs <= 0) {
            return false
        }

        if (session.summary.movementMean < 0) {
            return false
        }

        return true
    }
}