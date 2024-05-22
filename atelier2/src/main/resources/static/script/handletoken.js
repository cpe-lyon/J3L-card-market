const loginUrl = "/login.html"

const currentUrl = new URL(location.href);
if (currentUrl.searchParams.has("token")){
    localStorage.setItem("cardToken", currentUrl.searchParams.get("token"));
    currentUrl.searchParams.delete("token");
    history.replaceState(history.state, null, currentUrl.toString())
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

profile.addEventListener('click', function() {
    const isConfirmed = window.confirm('Êtes-vous sûr de vouloir vous déconnecter ?');

    if (isConfirmed) {
        localStorage.removeItem('cardToken');

        window.location.href = '/';
    }
});