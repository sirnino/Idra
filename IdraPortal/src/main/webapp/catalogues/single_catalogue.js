/*******************************************************************************
 * Idra - Open Data Federation Platform
 *  Copyright (C) 2018 Engineering Ingegneria Informatica S.p.A.
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
angular.module("IdraPlatform").controller('CatalogueCtrl',['$scope','$http','config','$rootScope','dialogs','$timeout','$modal','$window','ODMSNodesAPI',function($scope,$http,config,$rootScope,dialogs,$timeout,$modal,$window,ODMSNodesAPI){

	if($rootScope.mode == undefined || ($rootScope.mode!="create" && $rootScope.nodeToUpdate == undefined)){
		$window.location.assign('#/catalogues');
		return;
	}
	
	$scope.types=config.NODE_TYPES.split(',');
	$scope.grades=config.FEDERATION_LEVEL.split(',');
	$scope.updatePeriods=[{text:'1 hour',value:'3600'},{text:'1 day',value:'86400'},{text:'1 week',value:'604800'}];

	$scope.dcatProfiles = [{text:'DCATAP',value:'DCATAP'},{text:'DCATAP_IT',value:'DCATAP_IT'}];
	$scope.ODMSCategories = [{text:'Municipality',value:'Municipality'},{text:'Province',value:'Province'},{text:'Private Institution',value:'Private Institution'},{text:'Public Body',value:'Public Body'},{text:'Region',value:'Region'}];
	$scope.activeMode = [{text:'Yes',value:true},{text:'No',value:false}];
	
	$scope.showMessageUrl = false;
	$scope.showMessageName = false;

	$scope.pageTitle='';
		
	$scope.imageRead="";
	$scope.dumpInvalid=false;
	$scope.dump=null;
	
	/*NEW Upload file methods for new Catalogues type*/
	
	$scope.fileUploadWEB = function(fileEl){
		var files = fileEl.files;
		  var file = files[0];
		  console.log(file);
		  if(file.name.endsWith(".json")){
			  var reader = new FileReader();

			  reader.onloadend = function(evt) {
				  if (evt.target.readyState === FileReader.DONE) {
					  $scope.$apply(function () {
						  $scope.node.sitemap = JSON.parse(evt.target.result);
					  });
				  }
			  };

			  reader.readAsText(file);
		  }else{
			  dialogs.error("Wrong file","Please provide a .json file");
			  return;
		  }
	};
	
	$scope.fileUploadDUMP = function(fileEl){
		var files = fileEl.files;
		var file = files[0];
		console.log(file);
		if(file.name.endsWith(".rdf") || file.name.endsWith(".xml")){
				$scope.dump=file;
		}else{
			dialogs.error("Wrong file","Please provide an .xml or .rdf file");
			return;
		}
	};
	
	/*END*/
	
	$scope.toDataUrl = function(elem){
		var reader = new FileReader();
        reader.onload = $scope.imageIsLoaded;
        reader.readAsDataURL(element);
	}
	
	 $scope.imageUpload = function(element){
	        var reader = new FileReader();
	        reader.onload = $scope.imageIsLoaded;
	        reader.readAsDataURL(element.files[0]);
	    }
	 
	    $scope.imageIsLoaded = function(e){
	        $scope.$apply(function() {
	            $scope.imageRead=e.target.result;
	        });
	    }

	    $scope.openCropModal = function(){
	    	var modalInstance = $modal.open({
				animation: true,
				templateUrl: 'ImageCrop.html',
				controller: 'CropCtrl',
				size: 'lg',
				resolve: {
					image: function(){
						return $scope.imageRead;
					}
				}
			});

			modalInstance.result.then(function (croppedImage) {
				$scope.imageRead = croppedImage;
			});
	    }
	    
	$scope.attachedFile = function(element) {
		$scope.$apply(function($scope) {
			$scope.file = element.files[0];   
		});     
		$scope.addFile();
	};

	$scope.uploadDataUrl = function(){
		var url = 'UploadImage';
		
		$http.post(url, {
			headers: {
				'Content-Type': 'application/json'
					},
			data: {
				"image": $scope.imageRead,
				"name": $scope.node.name
			}
		})
			.success(function(data, status, headers, config) { 
				$scope.node.image = data;
			})
			.error(function(data, status, headers, config) {
				console.log('error');
			})
	};
	
	$scope.addFile = function() {
		
		var url = 'UploadImage';
		var fd = new FormData();   
		fd.append("file",$scope.file);          
		$http.post(url, fd,{
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined} })
			.success(function(data, status, headers, config) { 
				$scope.node.image = data;
			})
			.error(function(data, status, headers, config) {
				console.log('error');
			})
	};
	
	
	if($rootScope.mode == "create" ){
		$scope.pageTitle='Add Catalogue';

		$scope.node={
				id:null,
				name:'',
				publisherName: '',
				nameInvalid:false,
				pubNameInvalid:false,
				nodeType:$scope.types[0],
				federationLevel:$scope.grades[0],
				host:'',
				hostInvalid:false,
				refreshPeriod:"",
				description:"",
				APIKey: 'prova-api-key',
				location:"",
				locationDescription:"",
				dcatProfile:'',
				image:{
					imageData: "data:image/png;base64,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"
				},
				sitemap:{},
				dumpURL:'',
				dumpFilePath:null,
				country:'',
				category:'',
				isActive:true
				};

		
		var req = {
				method: 'GET',
				url: config.ADMIN_SERVICES_BASE_URL+config.CONFIGURATION_SERVICE,
				headers: {
					'Content-Type': 'application/json',
					'Authorization': "Bearer "+$rootScope.token
				}
		};

		$scope.refreshPeriod ='';

		$rootScope.startSpin();
		$http(req).then(function(value){

			$scope.refreshPeriod = value.data.refresh_period;
			$scope.node.refreshPeriod = angular.copy($scope.refreshPeriod);
			
			$rootScope.stopSpin();		
			
		}, function(value){
			$rootScope.stopSpin();
			if(value.status==401){
				$rootScope.token=undefined;
			}else{
				$rootScope.showAlert('danger',value.data.userMessage);
			}
		});
			
	}else{
		
		$scope.node = angular.copy($rootScope.nodeToUpdate);
		$scope.pageTitle='Update Catalogue: '+$scope.node.name;
		$scope.node.nameInvalid=false;
		$scope.node.pubNameInvalid=false;
		$scope.node.hostInvalid=false;
		$scope.node.refreshPeriod = $rootScope.nodeToUpdate.refreshPeriod.toString();
		if($rootScope.nodeToUpdate.nodeType=='DCATDUMP')
			$scope.node.dcatProfile = $rootScope.nodeToUpdate.dcatProfile.toString();
		else
			$scope.node.dcatProfile='';
	}
	
	$scope.resetNode= function(){
		if($rootScope.nodeToUpdate == undefined){
			$scope.node={
					id:null,
					name:'',
					nodeType:$scope.types[0],
					nameInvalid:false,
					pubNameInvalid:false,
					federationLevel:$scope.grades[0],
					host:'',
					hostInvalid:false,
					refreshPeriod:$scope.refreshPeriod,
					description:"",
					location:"",
					dcatProfile:'',
					locationDescription:"",
					APIKey: 'prova-api-key',
					image: {
						imageData: "data:image/png;base64,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"
					},
					sitemap:{},
					dumpURL:'',
					dumpFilePath:null,
					country:'',
					category:'',
					isActive:true
			};
			
		}else{
		
			$scope.node = angular.copy($rootScope.nodeToUpdate);
			$scope.node.nameInvalid=false;
			$scope.node.pubNameInvalid=false;
			$scope.node.hostInvalid=false;
			$scope.node.refreshPeriod = $rootScope.nodeToUpdate.refreshPeriod.toString();
			if($rootScope.nodeToUpdate.nodeType=='DCATDUMP')
				$scope.node.dcatProfile = $rootScope.nodeToUpdate.dcatProfile.toString();
			else
				$scope.node.dcatProfile='';
		}
	}

	$scope.updateNode = function(data,node) {	
		
		if(node.name==''){
			$scope.node.nameInvalid=true;
			$scope.messageName="Node name required";
		} else if (node.publisherName=='') {
			$scope.node.pubNameInvalid=true;
			$scope.messageName="Publisher name required";
		}else {
			$scope.node.nameInvalid=false;
			$scope.node.pubNameInvalid=false;
			$scope.messageName="";
		}
		
		if($scope.node.nameInvalid || $scope.node.pubNameInvalid) return;
		
		if($scope.imageRead!=''){
			node.image.imageData = $scope.imageRead;
		}

		if($scope.node.sitemap != ''){
			node.sitemap = $scope.node.sitemap;
		}

		/* ******* Create the multipart request ************/
		
		var fd = new FormData();   
		fd.append("node",JSON.stringify(node));
		
		// TODO Insert dump file if node type is DCATDUMP
		fd.append("dump",'');  
				
		ODMSNodesAPI.updateODMSNode(node.id,fd).then(function(value){
				$rootScope.showAlert('success',"Catalogue updated!");
				$timeout(function(){
					$window.location.assign("#/catalogues");
				},1000);
				
			}, function(value){
				$rootScope.showAlert('danger',value.data.userMessage);
			});
	};


	$scope.isEqual = function(node1, node2){
		if(node1.federationLevel != node2.federationLevel) return false;
		else if(node1.name != node2.name) return false;
		else if(node1.nodeType != node2.nodeType) return false;
		else if(node1.host != node2.host) return false;
		else if(node1.description != node2.description) return false;
		else if(node1.refreshPeriod != node2.refreshPeriod) return false;
		else if($scope.imageRead!='') return false;
		else if(node1.location != node2.location) return false;
		else if(node1.publisherName != node2.publisherName) return false;
		else if(node1.locationDescription != node2.locationDescription) return false;
		else if(node1.category != node2.category) return false;
		else if(node1.country != node2.country) return false;
		else if( (node1.nodeType == node2.nodeType && node1.nodeType == "DCATDUMP") && node1.dumpURL != node2.dumpURL) return false;
		else if( (node1.nodeType == node2.nodeType && node1.nodeType == "DCATDUMP") && node1.dcatProfile != node2.dcatProfile) return false;
		else if( (node1.nodeType == node2.nodeType && node1.nodeType == "WEB") && node1.sitemap != node2.sitemap) return false;
		
		else return true;		
	}
	
	function isEqualField(f1,f2){
		if(f1==f2) return true;
		return false;
	}
		
//	$scope.checkNodeHost = function(){
//		console.log("checkNodeHost");
//		if($rootScope.mode=='create' && $scope.node!='')
//			return ODMSNodesAPI.hostExists($scope.node.host);
//		return false;
//	}
//	
//	$scope.checkNodeName=function(){	
//		if(($rootScope.mode=='create' && $scope.node.name!='')||($rootScope.mode=='update' && $scope.node.name!=$rootScope.nodeToUpdate.name)){
//			return ODMSNodesAPI.nameExists($scope.node.name);
//		}
//		return false;
//	}
	
	$scope.checkNodeHost=function(){
		
		if($rootScope.mode=='create' && $scope.node!=''){
			var tmpHost="";
			if($scope.node.host[$scope.node.host.length-1]=='/'){
				tmpHost=$scope.node.host.substring(0,$scope.node.host.length-1);
			}else{
				tmpHost=$scope.node.host+"/";
			}
			if($rootScope.urls.indexOf($scope.node.host.toLowerCase())>=0 || $rootScope.urls.indexOf(tmpHost.toLowerCase())>=0){
				return true;
			}
		}
		return false;
	}
	
	$scope.checkNodeName=function(){
		if($rootScope.mode=='create' && $scope.node.name!=''){
			if($rootScope.names.indexOf($scope.node.name.toLowerCase())>=0){
				return true;
			}
		}else if($rootScope.mode=='update'){
			if($rootScope.names.indexOf($scope.node.name.toLowerCase())>=0 && $scope.node.name!=$rootScope.nodeToUpdate.name ){
				return true;
			}
		}
		return false;
	}
	
	
	$scope.createNode = function(data,node){
		
		if(node.name==''){
			$scope.node.nameInvalid=true;
			$scope.messageName="Node name required";
		} else if (node.publisherName=='') {
			$scope.node.pubNameInvalid=true;
			$scope.messageName="Publisher name required";
		}else {
			$scope.node.nameInvalid=false;
			$scope.node.pubNameInvalid=false;
			$scope.messageName="";
		}

		if(node.host == ''){
			$scope.node.hostInvalid=true;
			$scope.messageUrl="Node url required";
		}else{
			$scope.node.hostInvalid=false;
			$scope.messageUrl="";
		}

		if($scope.node.nameInvalid || $scope.node.pubNameInvalid || $scope.node.hostInvalid) return;

		if(validateUrlForm(node)){

			switch(node.nodeType){
			case 'CKAN':
				node.federationLevel='LEVEL_3';
				break;
			case 'DKAN':
				node.federationLevel='LEVEL_2';
				break;
			case 'SOCRATA':
				node.federationLevel='LEVEL_2';
				break;
			case 'WEB':
				node.federationLevel='LEVEL_2';
				break;
			case 'DCATDUMP':
				node.federationLevel='LEVEL_2';
				break;
			default:
				break;
			}

			if(node.nodeType == 'WEB'){
				if(angular.equals({}, node.sitemap)){
					dialogs.error("File mandatory","Please upload the sitemap file");
					return;
				}
			}



			//$rootScope.startSpin();

			if($scope.imageRead != ''){
				node.image.imageData = $scope.imageRead;
			}

//			$scope.uploadDataUrl();



			/* ************ Create the multipart request *****************/

			var fd = new FormData();   

			if(node.nodeType == 'DCATDUMP'){
				if($scope.dump==null && node.dumpURL==''){
					dialogs.error("Dump mandatory","Please upload the dump file or a dump url");
					return;
				}else{

					if(node.dumpURL !='' && !validateUrl(node.dumpURL)){
						$scope.dumpInvalid=true;
						return;
					}else if(node.dumpURL=='' && $scope.dump!=''){
						node.dumpURL=null;
					}

//					node.dumpURL = $scope.dumpURL;
					fd.append("dump",$scope.dump);
				}
			}else{
				fd.append("dump",'');
			}

			fd.append("node",JSON.stringify(node));

				node.synchLock = 'FIRST';
				node.nodeState = "OFFLINE";
				node.datasetCount = 0;
				node.registerDate = new Date();
				node.lastUpdateDate = new Date();
				node.inserted=false; 
				$rootScope.nodeCreated.push(node);

				ODMSNodesAPI.addODMSNode(fd).then(function(){
					$rootScope.getNodes();
				}, function(value){

					if(value.status==401){
						$rootScope.token=undefined;
						dialogs.error("Authentication failed","Please login first");
					}

					if(value.status!=502){
						dialogs.error("Registration failed",value.data.userMessage);
						$rootScope.getNodes();
					}
					
				});
				$rootScope.startSpin();
				$timeout(function(){
					$rootScope.stopSpin();
					$window.location.assign("#/catalogues");
				},500);		

		}else{
			$scope.node.hostInvalid=true;
			$scope.showMessageUrl = true;
			$scope.messageUrl ="Please insert a valid url";
		}
	}

	$scope.back = function(){
			$window.location.assign('#/catalogues');
	}

	function validateUrlForm(node){

		//var reg = _^(?:(?:https?|ftp)://)(?:\S+(?::\S*)?@)?(?:(?!10(?:\.\d{1,3}){3})(?!127(?:\.\d{1,3}){3})(?!169\.254(?:\.\d{1,3}){2})(?!192\.168(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)(?:\.(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)*(?:\.(?:[a-z\x{00a1}-\x{ffff}]{2,})))(?::\d{2,5})?(?:/[^\s]*)?$_iuS;
		var reg = /^(http|https):\/\/[^ "]+$/;
		if(reg.test(node.host)){
			return true;
		}else{
			return false;
		}
	}
	
	function validateUrl(url){

		//var reg = _^(?:(?:https?|ftp)://)(?:\S+(?::\S*)?@)?(?:(?!10(?:\.\d{1,3}){3})(?!127(?:\.\d{1,3}){3})(?!169\.254(?:\.\d{1,3}){2})(?!192\.168(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)(?:\.(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)*(?:\.(?:[a-z\x{00a1}-\x{ffff}]{2,})))(?::\d{2,5})?(?:/[^\s]*)?$_iuS;
		var reg = /^(http|https):\/\/[^ "]+$/;
		if(reg.test(url)){
			return true;
		}else{
			return false;
		}
	}
	
//	$scope.country='ar';
//	$scope.flagImg='flag-icon-'+$scope.country.toLowerCase();
	
//	$scope.$watch('node.country',function(newVal,old){
//		console.log(old);
//		console.log(newVal);
//		$scope.flagImg='flag-icon-'+newVal.toLowerCase();
//	})
	
}]);

angular.module("IdraPlatform").controller('CropCtrl',["$scope",'image','$modalInstance',function($scope,image,$modalInstance){
	$scope.myImage = image;
	$scope.myCroppedImage = "";
	
	$scope.ok = function(){
		$modalInstance.close($scope.myCroppedImage);
	};
	
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
	
}]);

angular.module("IdraPlatform").controller('MessageCtrl',["$scope",'messages','name','nodeID','$modalInstance','$http','config','$rootScope',function($scope,messages,name,nodeID,$modalInstance,$http,config,$rootScope){
	$scope.messages = messages;
	$scope.name = name;
	$scope.nodeID = nodeID;
		
	$scope.deleteAllMessages=function(){
		var req = {
				method: 'DELETE',
				url: config.ADMIN_SERVICES_BASE_URL+config.NODES_SERVICE+"/"+$scope.nodeID.toString()+config.NODE_MESSAGES_SERVICE,
				headers: {
					'Content-Type': 'application/json',
					'Authorization': "Bearer " +$rootScope.token
				}};
		
		
		
		$http(req).then(function(value){
			$modalInstance.close();
		},function(){
			$modalInstance.close();
		});
	}
	
	$scope.deleteMessage=function(message,$index){
				
		var req = {
				method: 'DELETE',
				url: config.ADMIN_SERVICES_BASE_URL+config.GET_NODES_SERVICE+"/"+$scope.nodeID.toString()+config.NODE_MESSAGES_SERVICE+"/"+message.id,
				headers: {
					'Content-Type': 'application/json',
					'Authorization': "Bearer " +$rootScope.token
				}};
		
		
		
		$http(req).then(function(value){
			
			var index=-1;
			
			for(i=0; i<$scope.messages.length; i++ ){
				if($scope.messages[i].id == message.id)
					index=i;
			}
			
			if(index!= -1){
				$scope.messages.splice(index,1);
			}
			
			if($scope.messages.length==0){
				$modalInstance.close();
			}
			
		},function(){
		});
	}
	
	$scope.ok = function(){
		$modalInstance.close();
	};
	
	$scope.cancel = function () {
		$modalInstance.close();
	};
	
}]);