/**
 *
 */
		let registerButton = document.getElementById("register");

		registerButton.onclick = () => {

			if(window.confirm("登録しますか？")){
				return true;
			}else{
				return false;
			}

		};

		let csvRegisterButton = document.getElementById("csvRegister");

		csvRegisterButton.onclick = () => {

			if(window.confirm("登録しますか？")){
				return true;
			}else{
				return false;
			}

		};

		let updateButton = document.getElementById("update");

		updateButton.onclick = () => {

			if(window.confirm("更新しますか？")){
				return true;
			}else{
				return false;
			}

		};

		let noUpdate = document.getElementById("noUpdate");

		noUpdate.onclick = () => {

			if(window.confirm("1231515？")){
				return true;
			}else{
				return false;
			}

		};




