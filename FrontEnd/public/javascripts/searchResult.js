function clearSearch(){
    //找到搜尋欄的input
    var userInput = document.querySelector('.search-input').value;

    //清空搜尋欄的input
    userInput.value = '';
}