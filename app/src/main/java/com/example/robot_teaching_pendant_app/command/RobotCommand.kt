package com.example.robot_teaching_pendant_app.command

open class RobotCommand(val robotCommand: CommandType) {

    val type = robotCommand

    //비활성화 버튼 사용 시, On됩니다.
    //이후 명령어를 읽는 메서드를 구현 시, 해당 Boolean이 On 되어있으면, 명령어를 무시하고 다음 명령어를 읽습니다.
    var isDisable: Boolean? = null

    //명령어 인스턴스가 가지게 될 하위 명령어 리스트입니다.
    val childCommands = mutableListOf<RobotCommand>()

    var turn: Int? = null
}