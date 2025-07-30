import java.io.File
import kotlin.system.exitProcess

val repoUrl = "https://github.com/Mash-Up-Android/object-kotlin"
val docsDir = File("docs")
val readmeFile = File("README.md")


fun generateReadme() {
    val content = StringBuilder()
    generateHeaderMessage(content)
    generateMainMessage(content)
    generateFooterMessage(content)
    overrideReadMeFile(content)
}

fun generateHeaderMessage(content: StringBuilder) {
    content.appendWithLineBreak("<h1 align=\"center\">오브젝트 : 코드로 이해하는 객체지향 설계</h1>")
    content.appendWithLineBreak("📚🚨📚🚨📚🚨📚🚨📚🚨📚🚨📚🚨📚🚨📚🚨📚\n")
    insertSectionDivider(content)
}

fun generateMainMessage(content: StringBuilder) {
    val folderList = docsDir.listFiles() ?: run {
        println("폴더가 아무것도 없는뎁쇼?🐗")
        exitProcess(0)
    }

    folderList.asSequence().filter { it.isDirectory }.sortedBy { it.name }.forEach { folder ->
        content.appendWithLineBreak("## ${folder.name}")
        appendFolderContent(content, folder, true)
        insertSectionDivider(content)
    }
}

fun generateFooterMessage(content: StringBuilder) {
    content.appendWithLineBreak("## Contributors")
    content.appendWithLineBreak("<table>")
    content.appendWithLineBreak("    <tr align=\"center\">")
    content.appendWithLineBreak("        <td><B>유호현<B></td>")
    content.appendWithLineBreak("        <td><B>이재성<B></td>")
    content.appendWithLineBreak("    </tr>")
    content.appendWithLineBreak("    <tr align=\"center\">")
    content.appendWithLineBreak("        <td>")
    content.appendWithLineBreak("          <img src=\"https://github.com/fbghgus123.png?size=100\">")
    content.appendWithLineBreak("            <br>")
    content.appendWithLineBreak("            <a href=\"https://github.com/fbghgus123\"><I>fbghgus123</I></a>")
    content.appendWithLineBreak("        </td>")
    content.appendWithLineBreak("        <td>")
    content.appendWithLineBreak("            <img src=\"https://github.com/JaesungLeee.png?size=100\">")
    content.appendWithLineBreak("            <br>")
    content.appendWithLineBreak("            <a href=\"https://github.com/JaesungLeee\"><I>JaesungLeee</I></a>")
    content.appendWithLineBreak("        </td>")
    content.appendWithLineBreak("    </tr>")
    content.appendWithLineBreak("</table>")
}

fun appendFolderContent(content: StringBuilder, folder: File, isFirstCall: Boolean) {
    val items = folder.listFiles()?.sortedBy { it.name }
    items?.forEach { item ->
        if (item.isDirectory) {
            content.appendWithLineBreak("### ${item.name}")
            appendFolderContent(content, item, false)
        } else if (item.isFile) {
            // 내부 폴더와 파일 사이 구획을 나누기 위한 방법
            if (isFirstCall) content.appendWithLineBreak("### ${folder.name} 폴더없는 친구들")
            val filePath = "tree/main/docs/${folder.relativeTo(docsDir).path}/${item.name}"
            val fileUrl = "$repoUrl/$filePath"
            content.appendWithLineBreak("- [${item.name}]($fileUrl)")
        }
    }
}

fun overrideReadMeFile(content: StringBuilder) {
    readmeFile.writeText(content.toString())
    println("리드미 업데이트 완료 히히히")
}

fun insertSectionDivider(content: StringBuilder) {
    content.append("\n\n\n")
}

fun StringBuilder.appendWithLineBreak(value: String) {
    append(value + "\n")
}

// 최종 실행
generateReadme()
