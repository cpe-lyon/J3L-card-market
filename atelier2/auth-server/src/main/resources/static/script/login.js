const searchParams = new URLSearchParams(location.search.substr(1));
const redirectUrl = searchParams.has("redirect")? searchParams.get("redirect") : location.origin;

/**
 * @type {Element[]}
 */
const registerOnlies = document.querySelectorAll(".register-only");
/**
 * @type {Element[]}
 */
const loginOnlies = document.querySelectorAll(".login-only");

let isRegister = false;

/**
 * @param {boolean} isRegister
 */
function refreshRegister(){
    let onlies = [registerOnlies, loginOnlies];
    if (isRegister) onlies = onlies.reverse();
    onlies[0].forEach(e => e.style.display = "none");
    onlies[1].forEach(e => e.style.display = "inherit");
}

document.querySelectorAll('input[name="register"]').forEach(function(radioButton) {
    radioButton.addEventListener('change', function(event) {
        isRegister = event.target.value == 'true';
        refreshRegister();
    });
});


/**
 * @type {HTMLFormElement}
 */
const form = document.querySelector("#loginForm");
function getAuthHeader(){
    const formData = new FormData(form);
    const rawToken = `${formData.get("username")}:${formData.get("password")}`;
    return `Basic ${btoa(rawToken)}`;
}

form.addEventListener("submit", event => {
    event.preventDefault();

    const formData = new FormData(form);

    /**
     * @type {redirect: string} | {surname:string, avatarUrl:string, redirect:string}
     */
    let params = {redirect: redirectUrl}
    if (isRegister) params = {
        ...params,
        surname: formData.get("surname"),
        avatarUrl: formData.get("avatarUrl")
    };
    const processedFormData = new FormData();
    fetch(isRegister ? "/register": "/login", {
        method: form.method,
        body: JSON.stringify(params),
        headers: {
            Accept: '*/*',
            Authorization: getAuthHeader()
        }
    }).then(res => {
        if (res.status >= 400) throw res.body;
        if (res.status >= 300 && res.headers.has("Location")){
            location.href = res.headers.get("Location")
        }
    }).catch(reason => console.error(reason));
});

refreshRegister();