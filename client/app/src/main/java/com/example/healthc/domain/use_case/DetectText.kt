package com.example.healthc.domain.use_case

import com.example.healthc.domain.model.text_detection.DetectTextResult
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import com.example.healthc.utils.toIngredientEng
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DetectText @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
){
    suspend operator fun invoke(recognizedText: String, isEnglish: Boolean) : DetectTextResult {
        val userInfo = userRepository.getUserInfo(requireNotNull(firebaseAuth.uid))
        val detectedList = ArrayList<String>()
        when(userInfo){
            is Resource.Success -> {
                val userAllergies = if (isEnglish) { // 만약 재료가 영어인 경우
                    userInfo.result.allergy.map{ it.toIngredientEng()}
                }
                else userInfo.result.allergy

                userAllergies.onEach { // 알러지 성분이 포함되었는지 검사
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
                        detectedList = userInfo.result.allergy // 성공시 유저 알레르기 정보 반환
                    )
                }
            }

            is Resource.Failure -> {
                return DetectTextResult(false, false, emptyList())
            }

            is Resource.Loading -> {
                return DetectTextResult(false, false, emptyList())
            }
        }
    }
}