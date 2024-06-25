import java.util.Scanner

class AppLogic (){
    private val scanner = Scanner(System.`in`)
    private var input = ""
    private val archiveList: MutableList<Archive> = mutableListOf()

    fun start(){
        println("Добро пожаловать в \"Заметки\"!\n" +
                "Для начала работы, создайте свой первый архив!\n")
        addArchive()

        println("Меню выбора архива\n" +
                "-------------------")
        while (true){
            showChoiceMenu(archiveList)
            input = scanner.nextLine()
            when(input){
                "1" -> addArchive()
                "2" -> break
                else -> {
                    val index = input.toIntOrNull()?.minus(3)
                    if (index == null || index < 0 || index > archiveList.size){
                        inputError()
                    }
                    else{
                        notesMenu(index)
                    }
                }
            }
        }
    }

    private fun notesMenu(archive: Int){
        println("Архив: \"${archiveList[archive].title}\"\n" +
                "Меню выбора заметки\n" +
                "-------------------")
        while (true){
            showChoiceMenu(archiveList[archive].notes)
            input = scanner.nextLine()
            when(input){
                "1" -> addNote(archive)
                "2" -> break
                else -> {
                    val index = input.toIntOrNull()?.minus(3)
                    if (index == null || index < 0 || index > archiveList[archive].notes.size){
                        inputError()
                    }
                    else{
                        showNote(archiveList[archive].notes[index])
                    }
                }
            }
        }
    }

    private fun showNote(note: Note){
        println("Заметка:\n" +
                "\"${note.title}\"\n\n" +
                "${note.text}\n\n" +
                "Чтобы вернуться к предыдущему экрану, нажмите любую кнопку")
        scanner.nextLine()
    }

    private fun addArchive(){
        println("Меню создания архива\n" +
                "Чтобы выйти без сохранения, введите q\n" +
                "-------------------")
        while (true){
            println("Заголовок:")
            val title = scanner.nextLine()
            if (title == "q") return
            if (checkInputError(title)) continue
            archiveList.add(Archive(title, mutableListOf()))
            break
        }
        println("Архив создан!")
    }

    private fun addNote(index: Int){
        println("Меню создания заметки\n" +
                "Чтобы выйти без сохранения, введите q\n" +
                "-------------------")
        while (true){
            println("Заголовок:")
            val title = scanner.nextLine()
            if (title == "q") return
            if (checkInputError(title)) continue
            println("Текст:")
            val text = scanner.nextLine()
            if (text == "q") return
            if (checkInputError(text)) continue
            archiveList[index].notes.add(Note(title, text))
            println("Заметка добавлена!")
            break
        }
    }

    private fun <T : HasTitle> showChoiceMenu(list: MutableList<T>){
        println("1. Создать")
        println("2. Закрыть меню")
        for ((index, elem) in list.withIndex()){
            println("${index + 3}. \"${elem.title}\"")
        }
    }

    private fun checkInputError(input: String): Boolean{
        if (input == ""){
            inputError()
            return true
        }
        return false
    }

    private fun inputError(){
        println("Некорректный ввод. Попробуйте еще раз!")
    }
}