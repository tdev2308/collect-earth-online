
var ctlDashboard = function($scope) {
  $scope.formID = "dashboard";
  $scope.emailID = "email"
  $scope.passwordID = "password";
  $scope.passwordConfirmID = "password-confirmation";
  $scope.passwordCurrentID = "current-password";

  $scope.phEmail = "Email Address";
  $scope.phNewPwd = "New Password";
  $scope.phConfPwd = "New Password Confirmation";
  $scope.phCurrPwd = "Current Password";
  $scope.tpEmail = "email";
  $scope.tpPassword = "password";

  $scope.projects = new Array();
  populateProjects($scope.projects);

  $scope.map_config = new mapConfig;
  $scope.map_config.div_name = "image-analysis-pane";
  $scope.map_config.center_coords = [102.0, 17.0];
  $scope.map_config.zoom_level = 5;

//  map_utils.set_current_imagery('Mekong River Region');
// map_utils.draw_polygon('DigitalGlobeRecentImagery+Streets');
  map_utils.digital_globe_base_map($scope.map_config);
}

function updateUserInfo($scope) {
	$scope.updateMessage = "User Password Has Been Reset";
	return false;
}

function mapConfig(divName, centerCoords, zoomLevel){
	this.div_name = divName;
	this.center_coords = centerCoords;
	this.zoom_level = zoomLevel;
}

function populateProjects(_projects) {
	_projects.push({id:1,name:'Mekong River Region'})
	_projects.push({id:12,name:'Mekong_River_Sample'});
	_projects.push({id:14,name:'Lower Mekong Region'});
	_projects.push({id:15,name:'Myanmar Landcover Classification'});
	_projects.push({id:16,name:'MK Laos'});
	_projects.push({id:18,name:'Classification of Land Cover'});
	_projects.push({id:19,name:'Cambodia'});
	_projects.push({id:20,name:'Cambodia Land Cover'});
	_projects.push({id:23,name:'MK_VN'});
	_projects.push({id:24,name:'DN_VN'});
	_projects.push({id:25,name:'CM_VN'});
	_projects.push({id:26,name:'Myanmar Landcover'});
	_projects.push({id:37,name:'DN2'});
	_projects.push({id:38,name:'DN2_2'});
	_projects.push({id:39,name:'FAO Regional Subset Collection'});
	_projects.push({id:40,name:'FAO_Test'});
	_projects.push({id:41,name:'FAO Regional Subset Collection v2'});
	_projects.push({id:42,name:'FAO Test 2'});
	_projects.push({id:45,name:'Myanmar_test'});
	_projects.push({id:47,name:'Gridded Sample Test'});
	_projects.push({id:49,name:'SCO_test'});
	_projects.push({id:50,name:'Bing Maps Test'});
	_projects.push({id:53,name:'HB Blowdown 2'});
	_projects.push({id:54,name:'HB Blowdown 3'});
	_projects.push({id:55,name:'TEST FAO'});

}


angular
   .module('collectEarth')
   .controller('ctlDashboard', ctlDashboard)
//   .directive('passwordReset', passwordReset);