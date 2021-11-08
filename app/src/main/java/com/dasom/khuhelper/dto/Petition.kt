package com.dasom.khuhelper.dto

import java.io.Serializable

class Petition : Serializable {
    var key: String? = null
    var username: String? = null
    var useremail: String? = null
    var title: String? = null
    var content: String? = null
    var csId // 충전소 id
            : String? = null
    var csName // 충전소 이름
            : String? = null
    var reply // 관리자 답변
            : String? = null
    var isCheck = false

    constructor() {}
    constructor(
        username: String?,
        useremail: String?,
        title: String?,
        content: String?,
        csId: String?,
        csName: String?
    ) {
        this.username = username
        this.useremail = useremail
        this.title = title
        this.content = content
        this.csId = csId
        this.csName = csName
        isCheck = false
        reply = ""
    }
}