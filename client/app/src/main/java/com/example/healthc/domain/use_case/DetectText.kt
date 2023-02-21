package com.example.healthc.domain.use_case

import com.example.healthc.domain.model.detect_text.DetectTextResult
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DetectText @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
){
    suspend operator fun invoke(recognizedText: String) : DetectTextResult {
        val userInfo = userRepository.getUserInfo(requireNotNull(firebaseAuth.uid))
        val detectedList = ArrayList<String>()
        when(userInfo){
            is Resource.Loading -> {}

            is Resource.Success -> {
                // 알러지 성분이 포함되었는지 검사
                userInfo.result.allergy.onEach {
                    if(recognizedText.contains(it)){
                        detectedList.add(it)
                    }
                }
                // 만약 포함 되있는 경우
                return if(detectedList.isNotEmpty()){
                    DetectTextResult(
                        successful = true,
                        detected = true,
                        detectedList = detectedList
                    )
                }
                else{
                    DetectTextResult(
                        successful = true,
                        detected = false,
                        detectedList = emptyList()
                    )
                }
            }

            is Resource.Failure -> {
                return DetectTextResult(false, false, emptyList())
            }
        }
        return DetectTextResult(false, false, emptyList())
    }
}