function searchResult() {
    // 重定向到搜索结果介面
    window.location.href = '/searchResult';
}
document.querySelector('.search-button').addEventListener('click', function(){
    let input = document.querySelector('.search-input').value;
    
    fetch('http://localhost:8080/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            input: input
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Result:', data.result);
    })
    .catch(error => {
        console.error('Error:', error);
    })
    
})

