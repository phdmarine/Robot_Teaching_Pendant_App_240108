package com.example.robot_teaching_pendant_app.make

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.robot_teaching_pendant_app.R
import com.example.robot_teaching_pendant_app.databinding.MakeDefaultFragmentBinding
import com.example.robot_teaching_pendant_app.make.JogFragment
import com.example.robot_teaching_pendant_app.make.JogModeFragment
import com.example.robot_teaching_pendant_app.system.JogState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MakeDefaultFragment : Fragment(), JogFragment.GoHomeListener, JogFragment.RefreshEtListener, JogFragment.RefreshJogListener, PositionViewerFragment.FragmentCommunicationInterface {








    private var _binding: MakeDefaultFragmentBinding? = null
    private val defBinding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MakeDefaultFragmentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return defBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //우측 상단 기능 카테고리 버튼들
        val defAllBt = defBinding.defAllBt
        val defMoveBt = defBinding.defMoveBt
        val defFuncBt = defBinding.defFuncBt
        val defOtherBt = defBinding.defOtherBt
        val searchBox = defBinding.searchBox

        //기능 버튼 아이콘들이 출력 될 레이아웃
        val defIconView = defBinding.defIconView

        //Jog Controller를 삽입 할 보여 줄 View 입니다.
        val jogViewer = defBinding.jogControllerView

        //defaultFragment 하위에 있는 긴급 정지 버튼과 빠른Home 기능 버튼입니다.
        val makeEstopBt = defBinding.makeEstopBt
        val makeQhomeBt = defBinding.makeQhomeBt

        val jogModeView = defBinding.jogModeView
        val positionView = defBinding.positionViewer

        //조그(Global, Local, User, Joint) 를 선택하는 버튼들을 변수에 초기화 시킵니다.
        val jogGlobalBt = defBinding.jogGlobalBt
        val jogLocalBt = defBinding.jogLocalBt
        val jogUserBt = defBinding.jogUserBt
        val jogJointBt = defBinding.jogJointBt

        val jogButtonList = listOf(jogGlobalBt, jogLocalBt, jogUserBt, jogJointBt)

        //Jogstate Object에 있는 jogSelected 값에 따라서 버튼의 Disable 상태를 결정합니다.
        when (JogState.jogSelected) {
            JogState.JOG_GLOBAL_SELECTED -> {
                jogGlobalBt.setBackgroundResource(R.drawable.color_gray_frame)
                jogGlobalBt.isEnabled = false
            }

            JogState.JOG_LOCAL_SELECTED -> {
                jogLocalBt.setBackgroundResource(R.drawable.color_gray_frame)
                jogLocalBt.isEnabled = false
            }

            JogState.JOG_USER_SELECTED -> {
                jogUserBt.setBackgroundResource(R.drawable.color_gray_frame)
                jogUserBt.isEnabled = false
            }

            JogState.JOG_JOINT_SELECTED -> {
                jogJointBt.setBackgroundResource(R.drawable.color_gray_frame)
                jogJointBt.isEnabled = false
            }
        }



        if (savedInstanceState == null) { // <- saveInstanceState 가 Null 이면 프래그먼트가 처음 생성됨을 의미.

            //Jog Fragment 를 childFragment 형태로 불러옵니다.
            val fragment = JogFragment()
            childFragmentManager.beginTransaction()
                .replace(jogViewer.id, fragment)
                .commit()


            //Jog Mode Fragment 를 childFragment 형태로 불러옵니다.
            val jogModeFragment = JogModeFragment()
            childFragmentManager.beginTransaction()
                .replace(jogModeView.id, jogModeFragment)
                .commit()


            val positionViewerFragment = PositionViewerFragment()
            childFragmentManager.beginTransaction()
                .replace(positionView.id, positionViewerFragment)
                .commit()
        }


        //Quick Home 버튼의 클릭 리스너입니다. jogFragment의 goHome()메서드를 실행하여 로봇을 영점으로 보내고 editText를 영점 값으로 수정합니다.
        makeQhomeBt.setOnClickListener {
            onGoHome()
            refreshET()
        }


        //조그를 선택하는 4가지 버튼을 함수를 활용하여 그에 맞는 모드와 UI 로직을 가지게 합니다.
        jogGlobalBt.assignJogState(JogState.JOG_GLOBAL_SELECTED, jogButtonList)
        jogLocalBt.assignJogState(JogState.JOG_LOCAL_SELECTED, jogButtonList)
        jogUserBt.assignJogState(JogState.JOG_USER_SELECTED, jogButtonList)
        jogJointBt.assignJogState(JogState.JOG_JOINT_SELECTED, jogButtonList)


        // 아래는 우측 상단의 기능 카테고리 버튼들(all, move, func 등)을 클릭 시 리스너 동작들입니다.
        //Display 될 Icon은 Make 다렉토리 안에 있는 Icon.kt를 참고하십시요.
        defAllBt.setOnClickListener{
            displayIcons(allIcons)
        }

        defMoveBt.setOnClickListener{
            displayIcons(moveIcons)
        }


        //우측상단에 위치한 검색창 (EditText)기능을 정의합니다. ->검색한 결과에 맞는 ICON BUTTON들을 출력합니다.
        // Icon.kt에 있는 ICON BUTTON의 태그를 통해 검색
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // 입력 전
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val currentText = s.toString()  // s를 String으로 변환
                if (currentText.contains("\n") || currentText.contains("\r")) {
                    // CRLF나 엔터키가 포함된 경우, 해당 문자를 제거
                    val newText = currentText.replace("\n", "").replace("\r", "")
                    searchBox.setText(newText)

                    //커서를 텍스트의 끝으로 이동
                    searchBox.setSelection(newText.length)

                    // 필요하다면 사용자에게 경고 메시지 표시
                    Toast.makeText(context, "엔터키 입력은 허용되지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    filterAndDisplayIcons(currentText)
                }

            }
            override fun afterTextChanged(s: Editable) {
                // 입력 후
            }
        })

    }

    //검색한 ICON BUTTON들을 필터링하는 메서드입니다.
    fun filterAndDisplayIcons(query: String) {
        // query를 기반으로 icons 리스트를 필터링
        val filteredIcons = allIcons.filter { icon ->
            icon.title.contains(query, ignoreCase = true)  // 대소문자를 무시하고 포함 여부 확인
        }
        // 필터링된 아이콘 버튼들의 크기 등을 설정 한 뒤, 출력합니다.
        displayIcons(filteredIcons)
    }

    //각 기능의 ICON BUTTON들을 우측 상단의 SCROLL Layout에 생성하는 함수입니다. 인자값으로 Icon.kt 에 있는 ICON BUTTON 정보들의 리스트를 받게 됩니다.
    fun displayIcons(icons: List<Icon>) {
        val gridLayout = defBinding.gridLayout

        //카테고리 버튼이 클릭되면 먼저 레이아웃에 있는 뷰를 전부 제거합니다.
        gridLayout.removeAllViews()

        for (iconData in icons) {
            val iconButton = ImageButton(context)
            iconButton.setImageResource(iconData.imageRes)


            //FIT_CENTER는 이미지를 뷰의 중앙에 위치시키면서 뷰에 맞게 이미지를 조절합니다. 이미지의 가로세로 비율은 유지됩니다.
            //CENTER_INSIDE는 이미지가 뷰의 경계 내에 완전히 들어오도록 이미지 크기를 조절합니다. 이미지의 가로세로 비율 역시 유지됩니다.
            iconButton.scaleType = ImageView.ScaleType.CENTER_INSIDE
            iconButton.background = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                iconButton.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.icon_frame)
            }

            //아이콘의 사이즈, Margin 등, 필요한 파라미터 값을 정합니다.
            val params = GridLayout.LayoutParams()
            params.width = 150
            params.height = 150
            params.setMargins(10, 10, 10, 10)  // 마진 설정: 좌, 상, 우, 하

            // 아이콘 버튼의 레이아웃 파라미터에 위에서 설정한 params를 적용합니다.
            iconButton.layoutParams = params
            iconButton.background = null

            //아이콘 버튼의 리스너입니다. 아이콘 버튼들의 Action에 있는 동작을 취합니다.
            iconButton.setOnClickListener {
//                Toast.makeText(context, "${iconData.title} 버튼이 눌러졌습니다.", Toast.LENGTH_SHORT).show()

                //아이콘에 할당되니 특정 액션(람다 함수)를 실행합니다.
                iconData.action.invoke()
                //MakeActivity에 있는 commandTreeViewModel의 triggerUpdateEvenet 메서드를 호출합니다.
                //ViewModel 내의 _updateEvent를 업데이트하여 관련된 데이터가 변경되었음을 알림.
                (activity as? MakeActivity)?.commandTreeViewModel?.triggerUpdateTextViewEvent()
            }

            //Gridlayout 에 iconButton 을 추가합니다.
            gridLayout.addView(iconButton)
        }
    }

    //JogFragment 의 UI 갱신을 위해, refreshJogListener 인터페이스를 구현합니다. 해당 메서드는 JogFragment 의 setJog()메서드를 호출하여, 유저의 선택과 상황에 맞게 UI를 업데이트 해줍니다.
    override fun refreshJog() {
        val jogFragment = childFragmentManager.findFragmentById(defBinding.jogControllerView.id) as JogFragment

        activity?.runOnUiThread {
            jogFragment.setJog()
        }
    }

    //JogFragment 의 조그 값을 입력하는 EditText 갱신을 위해, refreshETListener 인터페이스를 구현합니다. 해당 메서드는 JogFragment 의 refreshEditText()메서드를 호출하여, 유저의 선택과 상황에 맞게 EditText를 고칩니다.
    //setJog()를 사용하면 되어서 추후 상황을 보고 삭제할 예정입니다.
    override fun refreshET() {
        val jogFragment = childFragmentManager.findFragmentById(defBinding.jogControllerView.id) as JogFragment

        activity?.runOnUiThread {
            jogFragment.refreshEditText()
        }
    }

    //JogFragment 의 GoHoneListener 인터페이스를 구현합니다. onGoHome() 메서드를 override하고 , 이 메서드는 goHome() 메서드를 불러와 실행합니다. 로봇을 지정한 영점으로 이동 시킵니다.
    interface GoHomeListener
    override fun onGoHome() {
        val jogFragment = childFragmentManager.findFragmentById(defBinding.jogControllerView.id) as JogFragment
        activity?.runOnUiThread {
            jogFragment.goHome()
        }
    }

    //PosotionViewerFragment의 setTextView()를 통해 TextView를 최신화 시키기 위한 함수입니다.
    override fun refreshTextView() {
        val positionViewerFragment = childFragmentManager.findFragmentById(defBinding.positionViewer.id) as? PositionViewerFragment
        activity?.runOnUiThread {
            positionViewerFragment?.setTextView()
        }
    }


    //조그를 선택하는 버튼들에 대한 UI 로직입니다. 본인은 비활성화 시키고, 나머지 3가지 버튼은 활성화 시켜줍니다. (중복 클릭 방지)
    //JOG UI는 JogFragment의 RefreshJogListener Interface를 구현하여, 그 안에 있는 setJog() 라는 메서드를 호출하여 업데이트 시킵니다.
    //Value(jogSelected가 어떤게 될 것인지, 사용할 버튼은 어떤 것들인지 리스트 형태로 받아오게 되고 로직을 수행합니다.
    fun ImageButton.assignJogState(value: Int, buttonList: List<ImageButton>) {
        //조그 버튼들에 대한 클릭 리스너
        this.setOnClickListener {

            //Object Jogstate 의 jogSelected(조그 선택상태) 변수를 value인자값으로 변경합니다.
            JogState.jogSelected = value

            for (otherButton in buttonList) {
                //버튼 리스트에 있는 버튼 중, 자기 자신이 클릭되면 Enable= False 하고, 배경색을 회색으로 변경하여 선택 되었음을 표시합니다.
                if (otherButton == this) {
                    otherButton.isEnabled = false
                    otherButton.setBackgroundResource(R.drawable.color_gray_frame) // 회색 배경 리소스

                    //어떤 좌표계의 조그가 선택되었는지 알려주는 Toast 메시지를 출력합니다.
                    when(JogState.jogSelected){
                        JogState.JOG_GLOBAL_SELECTED-> Toast.makeText(it.context, "Global 좌표계 Jog가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                        JogState.JOG_LOCAL_SELECTED-> Toast.makeText(it.context, "LOCAL 좌표계 Jog가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                        JogState.JOG_USER_SELECTED-> Toast.makeText(it.context, "USER 좌표계 Jog가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                        JogState.JOG_JOINT_SELECTED-> Toast.makeText(it.context, "JOINT 좌표계 Jog가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                    }


                    //눌러진 버튼을 제외한 나머지 버튼들을 Enable = True 시키고, 배경색을 바꾸어 클릭할 수 있음을 표시합니다.
                } else {
                    otherButton.isEnabled = true
                    otherButton.setBackgroundResource(R.drawable.public_button) // 기본 배경 리소스
                }
            }
            //Jog Fragment 에 있는 setJog()를 불러오는 함수입니다.
            refreshJog()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MakeDefault.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MakeDefaultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

