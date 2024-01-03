function clearSearch(){
    //找到搜尋欄的input
    var userInput = document.querySelector('.search-input');
        //清空搜尋欄的input
    userInput.value = '';
}
function handleRegionChange() {
    // 取得第一個下拉式選單的值
    var selectedRegion = document.getElementById("search-input").value;

    // 取得第二個下拉式選單的元素
    var districtSelect = document.getElementById("districtSelect");

    // 清空第二個下拉式選單的內容
    districtSelect.innerHTML = "";

    // 根據選擇的區域，動態添加相應的選項到第二個下拉式選單
    console.log("okokokok");
    switch (selectedRegion) {
      
        case "north":
            addOption(districtSelect, "台北市");
            addOption(districtSelect, "新北市");
            addOption(districtSelect, "基隆市");
            addOption(districtSelect, "新竹市");
            addOption(districtSelect, "新竹縣");
            addOption(districtSelect, "桃園市");
            addOption(districtSelect, "宜蘭縣");
            break;
        case "central":
            addOption(districtSelect, "台中市");
            addOption(districtSelect, "彰化縣");
            addOption(districtSelect, "苗栗縣");
            addOption(districtSelect, "南投縣");
            addOption(districtSelect, "雲林縣");
            break;
        case "south":
            addOption(districtSelect, "高雄市");
            addOption(districtSelect, "台南市");
            addOption(districtSelect, "嘉義市");
            addOption(districtSelect, "嘉義縣");
            addOption(districtSelect, "屏東縣");
            break;
        case "east":
            addOption(districtSelect, "花蓮縣");
            addOption(districtSelect, "台東縣");
            break;
        case "islands":
            addOption(districtSelect, "澎湖縣");
            addOption(districtSelect, "金門縣");
            addOption(districtSelect, "馬祖列島");
            break;
        default:
            // 如果未選擇區域，清空第二個下拉式選單
            addOption(districtSelect, "choose");
            break;
    }
}

function addOption(selectElement, optionText) {
    var option = document.createElement("option");
    option.text = optionText;
    selectElement.add(option);
}

document.addEventListener('DOMContentLoaded', () => {
    const results = JSON.parse(sessionStorage.getItem('searchResults'));
    console.log("success");
    if (results) {
        results.forEach(result => {
            console.log('標題:', result.title);
            console.log('連結:', result.link);
        })
    }
});
document.addEventListener('DOMContentLoaded', () => {
    const results = JSON.parse(sessionStorage.getItem('searchResults'));
    const resultsContainer = document.getElementById('searchResultsContainer');

    if (results && resultsContainer) {
        results.forEach(result => {
            // 創建一個包含標題和連結的區域
            const resultItem = document.createElement('div');
            resultItem.className = 'result-item';

            // 創建標題
            const titleElement = document.createElement('h2');
            const titleLink = document.createElement('a');
            // titleLink.href = result.link;  // 設定連結
            titleElement.textContent = result.title;  // 設定標題
            titleElement.appendChild(titleLink);

            // 創建連結
            const linkElement = document.createElement('a');
            linkElement.href = result.link;  // 設定連結
            linkElement.textContent = linkElement.href;  

            resultItem.appendChild(titleElement);
            resultItem.appendChild(linkElement);

            resultsContainer.appendChild(resultItem);
        });
    }
});
