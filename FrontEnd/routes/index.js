var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// POST请求处理重定向到搜索结果页面 B 的操作
router.post('/redirectToSearchResult', function(req, res, next) {
  // 执行重定向到搜索结果页面 B
  res.redirect('/searchResult');
});

module.exports = router;
