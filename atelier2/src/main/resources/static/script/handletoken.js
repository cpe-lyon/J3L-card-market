const loginUrl = "/login.html"

const searchParams = new URLSearchParams(location.search.substr(1));
if (searchParams.has("token")){
    localStorage.setItem("cardToken", searchParams.get("token"));
    searchParams.delete("token");
    location.search = "?"+searchParams.toString();
}

window.cardToken = localStorage.getItem("cardToken");
window.authHeader = {Authorization: `Bearer ${cardToken}`};


let profile = document.querySelector(".profile");

/**
 * @param {string} surname
 * @param {string} username
 * @param {string} avatar
 */
function updateProfile({surname, username, avatarUrl}){
    profile.querySelector("span").innerText = surname;
    if (avatarUrl !== "") profile.querySelector("img").src = avatarUrl;
}

/**
 * @param {Response} res
 * @returns {Response}
 */
window.loginRedirectHandler = async (res) => {
    if (res.status == 401 && (await res.text()) === "NOT LOGGED IN"){
        location.href = loginUrl;
    }
    return res;
};

fetch("/api/userInfo", {redirect: "manual", headers: authHeader})
    .then(loginRedirectHandler)
    .then(res => res.json())
    .then(userInfoDto => {
        updateProfile(userInfoDto);
    })