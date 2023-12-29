function searchResult() {
    // 重定向到搜索结果介面
    window.location.href = '/searchResult';
}

/*document.querySelectorAll('.search-input').forEach(function(input) {
    input.addEventListener('click', function() {
        document.querySelector('.search-box').classList.add('input-clicked');
    });
});*/

function handleRegionChange() {
    // 獲取選擇縣市的值
    var selectedRegion = document.getElementById('region');
    var selectedCity = document.getElementById('city');
    var option = new Option("");
    region.options.add(option);
  
    // 根據選擇的縣市，動態添加相應的區域選項
    var regionList=[{
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

    for(var i = 0; i < regionList.length; i++){
        var option = new Option(regionList[i].name, )
    } 

    /*switch (selectedRegion) {
      case 'north':
        addDistrictOption('台北市');
        addDistrictOption('新北市');
        addDistrictOption('基隆市');
        addDistrictOption('桃園市');
        addDistrictOption('新竹市');
        addDistrictOption('新竹縣');
        break;
      case 'central':
        addDistrictOption('台中市');
        addDistrictOption('彰化縣');
        addDistrictOption('南投縣');
        addDistrictOption('雲林縣');
        break;
      case 'south':
        addDistrictOption('台南市');
        addDistrictOption('高雄市');
        addDistrictOption('屏東縣');
        addDistrictOption('澎湖縣');
        break;
      case 'east':
        addDistrictOption('宜蘭縣');
        addDistrictOption('花蓮縣');
        addDistrictOption('台東縣');
        break;
      case 'islands':
        addDistrictOption('金門縣');
        addDistrictOption('馬祖列島');
        break;
      //default:
        // 如果未選擇縣市，不添加任何區域選項
        //break;
    }
}
  
function addDistrictOption(districtName) {
    var districtSelect = document.getElementById('districtSelect');
    var option = document.createElement('option');
    option.value = districtName;
    option.textContent = districtName;
    districtSelect.appendChild(option);
}*/
  
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

