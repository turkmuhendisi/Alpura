import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpura.api.RetrofitClientEducation
import com.example.alpura.screens.education.VideoModuleResponseDto
import kotlinx.coroutines.launch

class EducationViewModel : ViewModel() {
    var videos by mutableStateOf<List<VideoModuleResponseDto>>(emptyList())
        private set

    var selectedVideo by mutableStateOf<VideoModuleResponseDto?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadAllVideos() {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = RetrofitClientEducation.apiService.getAllVideos()
                println("GELEN VİDEOLAR: ${result.map { it.title }}")
                videos = result
            } catch (e: Exception) {
                println("Video API hatası: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun loadVideoById(id: Long) {
        viewModelScope.launch {
            try {
                selectedVideo = RetrofitClientEducation.apiService.getVideoById(id)
            } catch (_: Exception) { selectedVideo = null }
        }
    }
}