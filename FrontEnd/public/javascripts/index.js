function searchResult() {
    // 重定向到搜索结果介面
    //window.location.href = '/searchResult';
}

document.getElementById('region').addEventListener('change', handleRegionChange);

function handleRegionChange() {
    var regionSelect = document.getElementById('region');
    var selectedRegion = regionSelect.value;
    
    /*city.innerHTML = "";
    // 獲取選擇縣市的值
    var region = document.getElementById('region');
    var city = document.getElementById('city');
    var option = new Option("", "");
    region.options.add(option);
    var selectedRegionIndex = region.selectedIdex;

    //如果選擇地區會添加相對應的城市
    if(selectedRegionIndex > 0) {
        var cityList = regionList[selectedRegionIndex - 1].cityList;
        for(var i = 0; i < cityList.length; i++){
            var option = new Option(cityList[i], cityList[i]);
            city.add(option);
        }
    }

    document.getElementById('region').onchange = handleRegionChange;
  
    // 根據選擇的縣市，動態添加相應的區域選項
    var regionList=[
        {
        name:"北部",
            cityList:["台北市","新北市","基隆市","桃園市","新竹市", "新竹縣"]
        },
        {name:"中部",
            cityList:["台中市","彰化縣","南投縣", "雲林縣"]
        },
        {name:"南部",
            cityList:["台中市","彰化縣","南投縣"]
        },
        {name:"東部",
            cityList:["宜蘭縣","花蓮縣","台東縣"]
        },
        {name:"離島地區",
            cityList:["澎湖縣","金門縣","馬祖列島"]
        }
    ];

    /*for(var i = 0; i < regionList.length; i++){
        var option = new Option(regionList[i].name);
        region.options.add(option);
    } */

    switch (selectedRegion) {
      case 'north':
        addCityOption('台北市');
        addCityOption('新北市');
        addCityOption('基隆市');
        addCityOption('桃園市');
        addCityOption('新竹市');
        addCityOption('新竹縣');
        break;
      case 'central':
        addCityOption('台中市');
        addCityOption('彰化縣');
        addCityOption('南投縣');
        addCityOption('雲林縣');
        break;
      case 'south':
        addCityOption('台南市');
        addCityOption('高雄市');
        addCityOption('屏東縣');
        break;
      case 'east':
        addCityOption('宜蘭縣');
        addCityOption('花蓮縣');
        addCityOption('台東縣');
        break;
      case 'islands':
        addCityOption('澎湖縣');
        addCityOption('金門縣');
        addCityOption('馬祖列島');
        break;
      default:
        // 如果沒有選擇縣市，則不添加任何區域選項
        break;
    }
}

/*function changeCity() {
    cityList.length = 0;
    var cityList = regionList[region.selectedIdex].cityList;
    for(var i = 0; i < cityList.length; i ++){
        var option = new Option(cityList[i].name);
        cityList.options.add(option);
    }
    document.getElementById('city').onchange = changeCity;
}*/
  
function addCityOption(city) {
    var citySelect = document.getElementById('city');
    var option = document.createElement('option');
    option.value = city;
    option.textContent = city;
    citySelect.appendChild(option);
}
  
document.querySelector('.search-button').addEventListener('click', function(){
    let input = document.querySelector('.search-input').value;
    console.log('發送請求:', input); // 在發送請求之前打印

    fetch('http://localhost:8080/searchResult', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            input: input
        })
    })
    .then(response => {
        console.log('收到響應:', response); // 收到響應時打印
        if (response.ok) {
            return response.json(); // 確認響應狀態正常後解析 JSON
        } else {
            throw new Error('Network response was not ok.');
        }
    })
    .then(data => {
        console.log('處理數據:', data); // 處理數據時打印
        sessionStorage.setItem('searchResults', JSON.stringify(data));
        window.location.href = '/searchResult.html';
    })
    .catch(error => {
        console.error('Error:', error);
    })
    
})

