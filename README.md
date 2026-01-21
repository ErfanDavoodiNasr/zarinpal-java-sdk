<div dir="rtl" align="right">

<h1><span dir="ltr">SDK</span> جاوا زرین‌پال</h1>

<h2>معرفی</h2>
<p>این کتابخانه یک <span dir="ltr">SDK</span> سبک برای اتصال به درگاه پرداخت زرین‌پال در پروژه‌های <span dir="ltr">Spring Boot 3.5.7</span> است. هدف آن ساده‌سازی پرداخت، اعتبارسنجی زودهنگام و دریافت پاسخ‌های تایپ‌شده است.</p>
<p>این پروژه یک کتابخانه است و برنامه اجرایی ندارد؛ آن را در پروژه خود استفاده می‌کنید تا روی منطق کسب‌وکار تمرکز کنید.</p>

<h2>پیش‌نیازها</h2>
<ul>
  <li><span dir="ltr">Java 21</span></li>
  <li><span dir="ltr">Spring Boot 3.5.7</span></li>
</ul>

<h2>نصب</h2>

<h3>مرحله ۱: ساخت و نصب محلی</h3>
<p>ابتدا کتابخانه را در مخزن محلی <span dir="ltr">Maven</span> نصب کنید.</p>
<div dir="ltr" align="left">
<pre><code class="language-bash">mvn clean install</code></pre>
</div>

<h3>مرحله ۲: افزودن به پروژه مصرف‌کننده</h3>
<div dir="ltr" align="left">
<pre><code class="language-xml">&lt;dependency&gt;
  &lt;groupId&gt;com.ernoxin&lt;/groupId&gt;
  &lt;artifactId&gt;ZarinPal-java-SDK&lt;/artifactId&gt;
  &lt;version&gt;1.0.3&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>
</div>

<h2>پیکربندی</h2>
<p>پیکربندی به‌صورت <span dir="ltr">fail-fast</span> انجام می‌شود: اگر مقدارهای اجباری ناقص باشند، برنامه در زمان بالا آمدن متوقف می‌شود تا خطا به مرحله پرداخت نرسد.</p>

<h3>کلیدهای <span dir="ltr">application.properties</span></h3>
<table>
  <thead>
    <tr>
      <th>کلید</th>
      <th>الزامی</th>
      <th>پیش‌فرض</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">zarinpal.merchant-id</code></td>
      <td>بله</td>
      <td>-</td>
      <td>شناسه پذیرنده به‌صورت <span dir="ltr">UUID</span> با طول ۳۶ کاراکتر</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.callback-url</code></td>
      <td>بله</td>
      <td>-</td>
      <td>آدرس بازگشت پس از پرداخت، باید <span dir="ltr">http</span> یا <span dir="ltr">https</span> باشد</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.environment</code></td>
      <td>خیر</td>
      <td><code dir="ltr">PRODUCTION</code></td>
      <td>محیط اجرا: <code dir="ltr">PRODUCTION</code> یا <code dir="ltr">SANDBOX</code></td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.base-url.production</code></td>
      <td>خیر</td>
      <td><code dir="ltr">https://payment.zarinpal.com</code></td>
      <td>دامنه سرویس در محیط عملیاتی</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.base-url.sandbox</code></td>
      <td>خیر</td>
      <td><code dir="ltr">https://sandbox.zarinpal.com</code></td>
      <td>دامنه سرویس در محیط تست</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.operation-version</code></td>
      <td>خیر</td>
      <td><code dir="ltr">v4</code></td>
      <td>نسخه مسیر <span dir="ltr">API</span></td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.timeout.connect</code></td>
      <td>خیر</td>
      <td><code dir="ltr">10s</code></td>
      <td>مهلت اتصال</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.timeout.read</code></td>
      <td>خیر</td>
      <td><code dir="ltr">30s</code></td>
      <td>مهلت دریافت پاسخ</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.retry.enabled</code></td>
      <td>خیر</td>
      <td><code dir="ltr">false</code></td>
      <td>فعال‌سازی تلاش مجدد در خطاهای شبکه</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.retry.max-attempts</code></td>
      <td>خیر</td>
      <td><code dir="ltr">1</code></td>
      <td>تعداد تلاش‌ها در صورت فعال بودن <code dir="ltr">retry</code></td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.retry.backoff</code></td>
      <td>خیر</td>
      <td><code dir="ltr">0ms</code></td>
      <td>وقفه بین تلاش‌ها</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.http.user-agent</code></td>
      <td>خیر</td>
      <td><code dir="ltr">ZarinpalJavaSdk</code></td>
      <td>مقدار <span dir="ltr">User-Agent</span> در درخواست‌ها</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.max-amount-irt</code></td>
      <td>خیر</td>
      <td><code dir="ltr">100000000</code></td>
      <td>سقف مبلغ برای پرداخت با تومان (<span dir="ltr">IRT</span>)</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.max-amount-irr</code></td>
      <td>خیر</td>
      <td><code dir="ltr">1000000000</code></td>
      <td>سقف مبلغ برای پرداخت با ریال (<span dir="ltr">IRR</span>)</td>
    </tr>
    <tr>
      <td><code dir="ltr">zarinpal.min-wage-amount</code></td>
      <td>خیر</td>
      <td><code dir="ltr">10000</code></td>
      <td>حداقل مبلغ هر تسهیم در تسویه اشتراکی (ریال)</td>
    </tr>
  </tbody>
</table>

<p>محدودیت‌های مبلغ و تسهیم (مثل <code dir="ltr">zarinpal.max-amount-irt</code>، <code dir="ltr">zarinpal.max-amount-irr</code> و <code dir="ltr">zarinpal.min-wage-amount</code>) توسط SDK برای اعتبارسنجی استفاده می‌شوند و درگاه نیز محدودیت‌های خودش را اعمال می‌کند؛ در صورت تغییر این مقادیر، فقط تنظیمات را به‌روزرسانی کنید و نیازی به تغییر کد نیست.</p>

<h3><span dir="ltr">timeout</span> و <span dir="ltr">retry</span></h3>
<ul>
  <li><span dir="ltr">zarinpal.timeout.connect</span> زمان برقراری اتصال است و شامل <span dir="ltr">TCP/SSL</span> می‌شود.</li>
  <li><span dir="ltr">zarinpal.timeout.read</span> زمان انتظار برای دریافت پاسخ پس از اتصال است.</li>
  <li><span dir="ltr">retry</span> فقط روی خطاهای شبکه/ارتباطی فعال می‌شود و روی خطاهای منطقی درگاه یا کدهای پاسخ اجرا نمی‌شود.</li>
  <li><span dir="ltr">max-attempts</span> تعداد کل تلاش‌ها و <span dir="ltr">backoff</span> فاصله بین تلاش‌ها را مشخص می‌کند.</li>
  <li>هشدار: فعال کردن <span dir="ltr">retry</span> برای <span dir="ltr">requestPayment</span> ممکن است باعث ایجاد چند <span dir="ltr">authority</span> شود اگر درخواست اول در درگاه ثبت شده ولی پاسخ آن به شما نرسیده باشد.</li>
</ul>

<p>فرمت مدت‌زمان‌ها می‌تواند به صورت <code dir="ltr">500ms</code>، <code dir="ltr">2s</code> یا <code dir="ltr">1m</code> باشد.</p>

<h3>نمونه تنظیمات</h3>
<p>حداقل تنظیمات لازم:</p>
<div dir="ltr" align="left">
<pre><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback</code></pre>
</div>

<p>محیط تست:</p>
<div dir="ltr" align="left">
<pre><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.environment=SANDBOX</code></pre>
</div>

<h2>آموزش گام‌به‌گام پرداخت</h2>
<ol>
  <li>تزریق کلاینت</li>
  <li>ساخت درخواست پرداخت</li>
  <li>انتقال خریدار به درگاه</li>
  <li>بازگشت و وریفای پرداخت</li>
</ol>

<h3>گام ۱: تزریق کلاینت</h3>
<p>کلاینت به‌صورت خودکار ساخته می‌شود و کافی است آن را تزریق کنید.</p>
<div dir="ltr" align="left">
<pre><code class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
private final ZarinpalClient client;
}
</code></pre>
</div>

<h3>گام ۲: ساخت درخواست پرداخت</h3>
<p>حداقل ورودی لازم شامل مبلغ و توضیح است. اگر <code dir="ltr">callback-url</code> در تنظیمات تعریف شده باشد، ارسال آن در هر درخواست ضروری نیست.</p>
<p>اگر برای یک تراکنش خاص آدرس بازگشت متفاوتی دارید، مقدار <code dir="ltr">callbackUrl</code> را در همان درخواست تنظیم کنید.</p>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-1001",
        true,
        null
);

PaymentRequest request = PaymentRequest.builder(150000, "خرید اشتراک ماهانه")
.currency(ZarinpalCurrency.IRR)
.metadata(metadata)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>گام ۳: انتقال خریدار به درگاه</h3>
<div dir="ltr" align="left">
<pre><code class="language-java">String redirectUrl = client.buildRedirectUrl(result.authority());
</code></pre>
</div>

<h3>گام ۴: بازگشت و وریفای پرداخت</h3>
<p>فقط وقتی <code dir="ltr">Status</code> برابر <code dir="ltr">OK</code> است باید وریفای انجام شود و مقدار <code dir="ltr">amount</code> باید همان مبلغ تراکنش باشد.</p>
<div dir="ltr" align="left">
<pre><code class="language-java">public VerifyResult handleCallback(ZarinpalClient client, Map&lt;String, String&gt; params, long amount) {
    ZarinpalCallback callback = client.parseCallback(params);
    if (!callback.isOk()) {
        throw new IllegalStateException("پرداخت ناموفق یا لغو شده است");
    }
    VerifyRequest request = new VerifyRequest(amount, callback.authority());
    return client.verifyPayment(request);
}
</code></pre>
</div>

<p><span dir="ltr">parseCallback</span> کلیدهای <span dir="ltr">Authority</span> و <span dir="ltr">Status</span> را به‌صورت <span dir="ltr">case-insensitive</span> می‌خواند؛ اگر هرکدام ارسال نشده باشند یا مقدار معتبر نداشته باشند، <span dir="ltr">ZarinpalCallbackException</span> دریافت می‌کنید. ورودی می‌تواند از نوع <span dir="ltr">Map</span> یا <span dir="ltr">MultiValueMap</span> باشد.</p>

<h2>مدل‌ها و اعتبارسنجی</h2>
<p>همه متدها <span dir="ltr">merchant_id</span> را از تنظیمات می‌خوانند و در ورودی‌ها دریافت نمی‌کنند. در صورت نامعتبر بودن داده‌ها، خطای <span dir="ltr">ZarinpalValidationException</span> قبل از ارسال درخواست رخ می‌دهد.</p>

<h3><span dir="ltr">PaymentRequest</span></h3>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">amount</code></td>
      <td>long</td>
      <td>بله</td>
      <td>مبلغ تراکنش؛ باید مثبت باشد. سقف مبلغ بر اساس واحد پولی از تنظیمات خوانده می‌شود.</td>
    </tr>
    <tr>
      <td><code dir="ltr">description</code></td>
      <td>String</td>
      <td>بله</td>
      <td>الزامی و حداکثر ۵۰۰ کاراکتر.</td>
    </tr>
    <tr>
      <td><code dir="ltr">callbackUrl</code></td>
      <td>URI</td>
      <td>خیر (در درخواست)</td>
      <td>اگر مقدار ندهید از <code dir="ltr">zarinpal.callback-url</code> استفاده می‌شود؛ باید <span dir="ltr">http/https</span> باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">currency</code></td>
      <td><span dir="ltr">ZarinpalCurrency</span></td>
      <td>خیر</td>
      <td><span dir="ltr">IRR</span> یا <span dir="ltr">IRT</span>؛ در صورت عدم ارسال، رفتار پیش‌فرض درگاه اعمال می‌شود.</td>
    </tr>
    <tr>
      <td><code dir="ltr">referrerId</code></td>
      <td>String</td>
      <td>خیر</td>
      <td>کد معرف؛ در صورت ارسال نباید خالی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">metadata</code></td>
      <td><span dir="ltr">PaymentMetadata</span></td>
      <td>خیر</td>
      <td>اطلاعات تکمیلی خریدار و تنظیمات رفتاری تراکنش.</td>
    </tr>
    <tr>
      <td><code dir="ltr">cartData</code></td>
      <td><span dir="ltr">CartData</span></td>
      <td>خیر</td>
      <td>جزئیات سبد خرید؛ در صورت ارسال، <code dir="ltr">items</code> باید وجود داشته باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">wages</code></td>
      <td><span dir="ltr">List&lt;PaymentWage&gt;</span></td>
      <td>خیر</td>
      <td>تسویه اشتراکی بین شرکا.</td>
    </tr>
  </tbody>
</table>

<p>نام فیلدها در خروجی <span dir="ltr">JSON</span> به صورت <span dir="ltr">snake_case</span> ارسال می‌شود و مقادیر <span dir="ltr">null</span> حذف می‌شوند؛ بنابراین هر فیلدی را مقداردهی نکنید به درگاه ارسال نخواهد شد (مثلا <code dir="ltr">callbackUrl</code> به <code dir="ltr">callback_url</code> تبدیل می‌شود).</p>

<h3><span dir="ltr">PaymentMetadata</span></h3>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">mobile</code></td>
      <td>String</td>
      <td>خیر</td>
      <td>شماره موبایل خریدار؛ در صورت ارسال نباید خالی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">email</code></td>
      <td>String</td>
      <td>خیر</td>
      <td>ایمیل خریدار؛ در صورت ارسال نباید خالی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">orderId</code></td>
      <td>String</td>
      <td>خیر</td>
      <td>شناسه سفارش؛ در صورت ارسال نباید خالی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">autoVerify</code></td>
      <td>Boolean</td>
      <td>خیر</td>
      <td>اگر <span dir="ltr">true</span> باشد وریفای خودکار انجام می‌شود؛ اگر <span dir="ltr">false</span> باشد باید وریفای را خودتان فراخوانی کنید؛ اگر ارسال نشود رفتار بر اساس تنظیمات پنل است.</td>
    </tr>
    <tr>
      <td><code dir="ltr">cardPan</code></td>
      <td>String</td>
      <td>خیر</td>
      <td>برای محدود کردن پرداخت به یک کارت مشخص؛ باید ۱۶ کاراکتر از ارقام یا <span dir="ltr">X/x</span> باشد.</td>
    </tr>
  </tbody>
</table>

<h3><span dir="ltr">CartData</span></h3>
<p>اگر <span dir="ltr">cart_data</span> ارسال شود، آرایه <span dir="ltr">items</span> الزامی است و نباید خالی باشد. SDK جمع کل سبد خرید را با <span dir="ltr">amount</span> تطبیق نمی‌دهد و فقط ساختار و قوانین هر آیتم را بررسی می‌کند.</p>

<h4>آیتم‌ها (<span dir="ltr">items</span>)</h4>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">itemName</code></td>
      <td>String</td>
      <td>بله</td>
      <td>نام محصول یا خدمت؛ نباید خالی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">itemAmount</code></td>
      <td>long</td>
      <td>بله</td>
      <td>مبلغ هر واحد؛ باید مثبت باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">itemCount</code></td>
      <td>long</td>
      <td>بله</td>
      <td>تعداد واحدها؛ باید مثبت باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">itemAmountSum</code></td>
      <td>long</td>
      <td>بله</td>
      <td>مجموع مبلغ این آیتم؛ باید دقیقاً برابر <span dir="ltr">itemAmount * itemCount</span> باشد.</td>
    </tr>
  </tbody>
</table>

<h4>هزینه‌های اضافی (<span dir="ltr">added_costs</span>)</h4>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">tax</code></td>
      <td>Long</td>
      <td>خیر</td>
      <td>مالیات سبد خرید؛ در صورت ارسال باید غیرمنفی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">payment</code></td>
      <td>Long</td>
      <td>خیر</td>
      <td>کارمزد یا هزینه پرداخت؛ در صورت ارسال باید غیرمنفی باشد.</td>
    </tr>
    <tr>
      <td><code dir="ltr">transport</code></td>
      <td>Long</td>
      <td>خیر</td>
      <td>هزینه حمل یا ارسال؛ در صورت ارسال باید غیرمنفی باشد.</td>
    </tr>
  </tbody>
</table>

<h4>کسرها (<span dir="ltr">deductions</span>)</h4>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">discount</code></td>
      <td>Long</td>
      <td>خیر</td>
      <td>مبلغ تخفیف؛ در صورت ارسال باید غیرمنفی باشد.</td>
    </tr>
  </tbody>
</table>

<h3><span dir="ltr">PaymentWage</span> (تسویه اشتراکی)</h3>
<table>
  <thead>
    <tr>
      <th>فیلد (SDK)</th>
      <th>نوع</th>
      <th>الزامی</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">iban</code></td>
      <td>String</td>
      <td>بله</td>
      <td>شماره شبا ۲۶ کاراکتری که با <span dir="ltr">IR</span> شروع می‌شود.</td>
    </tr>
    <tr>
      <td><code dir="ltr">amount</code></td>
      <td>long</td>
      <td>بله</td>
      <td>مبلغ تسهیم؛ باید مثبت باشد و حداقل مقدار از <code dir="ltr">zarinpal.min-wage-amount</code> خوانده می‌شود.</td>
    </tr>
    <tr>
      <td><code dir="ltr">description</code></td>
      <td>String</td>
      <td>بله</td>
      <td>توضیح تسهیم؛ نباید خالی باشد.</td>
    </tr>
  </tbody>
</table>
<p>قوانین تسویه اشتراکی: اگر <span dir="ltr">wages</span> ارسال شود نباید خالی باشد، حداکثر ۵ آیتم مجاز است، مجموع تسهیم حداکثر ۹۹٪ مبلغ کل است و هر آیتم باید شماره شبا و مقدار معتبر داشته باشد.</p>

<h2>سناریوهای رایج</h2>
<p>نمونه‌های کوتاه برای نیازهای متداول:</p>

<h3>پرداخت با واحد پولی تومان</h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentRequest request = PaymentRequest.builder(10000, "افزایش اعتبار")
        .currency(ZarinpalCurrency.IRT)
        .build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>آدرس بازگشت اختصاصی برای یک تراکنش</h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-3001",
        null,
        null
);

PaymentRequest request = PaymentRequest.builder(120000, "پرداخت سفارش 3001")
.callbackUrl(URI.create("https://example.com/payment/callback"))
.metadata(metadata)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>کنترل رفتار وریفای با <span dir="ltr">metadata.auto_verify</span></h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-2002",
        false,
        null
);

PaymentRequest request = PaymentRequest.builder(50000, "خرید کالا")
.metadata(metadata)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>ارسال سبد خرید با <span dir="ltr">cart_data</span></h3>
<div dir="ltr" align="left">
<pre><code class="language-java">CartData cartData = new CartData(
        List.of(
                new CartItem("کفش ورزشی", 50000, 2, 100000),
                new CartItem("جوراب", 25000, 1, 25000)
        ),
        new CartAddedCosts(5000L, 1000L, 2000L),
        new CartDeductions(3000L)
);

PaymentRequest request = PaymentRequest.builder(150000, "پرداخت سفارش 1010")
.cartData(cartData)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>تسویه اشتراکی با <span dir="ltr">wages</span></h3>
<div dir="ltr" align="left">
<pre><code class="language-java">List&lt;PaymentWage&gt; wages = List.of(
        new PaymentWage("IR123456789123456478945165", 10000, "سهم شریک اول"),
        new PaymentWage("IR567891234564789451651234", 50000, "سهم شریک دوم")
);

PaymentRequest request = PaymentRequest.builder(200000, "تسویه اشتراکی")
.wages(wages)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>پرداخت با کارت مشخص</h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        null,
        null,
        "502229XXXXXX1234"
);

PaymentRequest request = PaymentRequest.builder(10000, "پرداخت با کارت مشخص")
.metadata(metadata)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>همکاری در فروش با <span dir="ltr">referrer_id</span></h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentRequest request = PaymentRequest.builder(70000, "پرداخت با کد معرف")
        .referrerId("REF-CODE-123")
        .build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h3>ذخیره کارت در درگاه با ارسال شماره موبایل</h3>
<div dir="ltr" align="left">
<pre><code class="language-java">PaymentMetadata metadata = new PaymentMetadata(
        "09120000000",
        "user@example.com",
        null,
        null,
        null
);

PaymentRequest request = PaymentRequest.builder(10000, "پرداخت با ذخیره کارت")
.metadata(metadata)
.build();

PaymentRequestResult result = client.requestPayment(request);
</code></pre>
</div>

<h2>تمام متدهای کلاینت</h2>
<table>
  <thead>
    <tr>
      <th>متد</th>
      <th>ورودی</th>
      <th>خروجی</th>
      <th>نکته مهم</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code dir="ltr">requestPayment</code></td>
      <td><code dir="ltr">PaymentRequest</code></td>
      <td><code dir="ltr">PaymentRequestResult</code></td>
      <td>ایجاد تراکنش و دریافت <span dir="ltr">authority</span></td>
    </tr>
    <tr>
      <td><code dir="ltr">buildRedirectUrl</code></td>
      <td><code dir="ltr">authority</code></td>
      <td><code dir="ltr">String</code></td>
      <td>آدرس نهایی از دامنه محیط و مسیر <code dir="ltr">/pg/StartPay/</code> ساخته می‌شود</td>
    </tr>
    <tr>
      <td><code dir="ltr">parseCallback</code></td>
      <td><code dir="ltr">Map</code> یا <code dir="ltr">MultiValueMap</code></td>
      <td><code dir="ltr">ZarinpalCallback</code></td>
      <td>خروجی فقط شامل <span dir="ltr">authority</span> و <span dir="ltr">status</span> است</td>
    </tr>
    <tr>
      <td><code dir="ltr">verifyPayment</code></td>
      <td><code dir="ltr">VerifyRequest</code></td>
      <td><code dir="ltr">VerifyResult</code></td>
      <td>کد <span dir="ltr">100</span> موفق و کد <span dir="ltr">101</span> قبلا وریفای شده است</td>
    </tr>
    <tr>
      <td><code dir="ltr">reversePayment</code></td>
      <td><code dir="ltr">ReverseRequest</code></td>
      <td><code dir="ltr">ReverseResult</code></td>
      <td>فقط تراکنش موفق در ۳۰ دقیقه اول و نیازمند ثبت آی‌پی سرور در پنل</td>
    </tr>
    <tr>
      <td><code dir="ltr">inquirePayment</code></td>
      <td><code dir="ltr">InquiryRequest</code></td>
      <td><code dir="ltr">InquiryResult</code></td>
      <td>فقط وضعیت را اعلام می‌کند و جایگزین وریفای نیست</td>
    </tr>
    <tr>
      <td><code dir="ltr">unverifiedPayments</code></td>
      <td>-</td>
      <td><code dir="ltr">UnverifiedResult</code></td>
      <td>حداکثر ۱۰۰ تراکنش موفق وریفای‌نشده برگردانده می‌شود</td>
    </tr>
    <tr>
      <td><code dir="ltr">calculateFee</code></td>
      <td><code dir="ltr">FeeCalculationRequest</code></td>
      <td><code dir="ltr">FeeCalculationResult</code></td>
      <td>فقط محاسبه کارمزد؛ مبلغ حداقل ۱۰۰۰ ریال</td>
    </tr>
  </tbody>
</table>

<h2>سرویس تست (Sandbox)</h2>
<ul>
  <li>برای تست، مقدار <code dir="ltr">zarinpal.environment</code> را برابر <code dir="ltr">SANDBOX</code> قرار دهید.</li>
  <li>در سندباکس، <span dir="ltr">authority</span> با حرف <span dir="ltr">S</span> شروع می‌شود.</li>
  <li>در سندباکس، <span dir="ltr">merchant-id</span> می‌تواند هر <span dir="ltr">UUID</span> معتبر باشد.</li>
</ul>

<h2>خطاها و Exceptionها</h2>

<h3>لیست کدهای درگاه</h3>
<table>
  <thead>
    <tr>
      <th>حوزه</th>
      <th>کد</th>
      <th>پیام انگلیسی</th>
      <th>شرح فارسی</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>PaymentReverse</td><td>63-</td><td>Maximum time for reverse this session is expired.</td><td>بازه ۳۰ دقیقه‌ای ریورس منقضی شده است</td></tr>
    <tr><td>PaymentReverse</td><td>62-</td><td>Terminal ip limit most be active.</td><td>آی‌پی سرور در پنل ثبت نشده است</td></tr>
    <tr><td>PaymentReverse</td><td>61-</td><td>Session is not in success status.</td><td>تراکنش موفق نیست یا قبلا ریورس شده است</td></tr>
    <tr><td>PaymentReverse</td><td>60-</td><td>Session can not be reversed with bank.</td><td>امکان ریورس با بانک وجود ندارد</td></tr>
    <tr><td>PaymentVerify</td><td>55-</td><td>Manual payment request not found.</td><td>تراکنش یافت نشد</td></tr>
    <tr><td>PaymentVerify</td><td>54-</td><td>Invalid authority.</td><td>اتوریتی نامعتبر است</td></tr>
    <tr><td>PaymentVerify</td><td>53-</td><td>Session is not this merchant_id session</td><td>پرداخت متعلق به این مرچنت نیست</td></tr>
    <tr><td>PaymentVerify</td><td>52-</td><td>Oops!!, please contact our support team</td><td>خطای غیر منتظره</td></tr>
    <tr><td>PaymentVerify</td><td>51-</td><td>Session is not valid, session is not active paid try.</td><td>پرداخت ناموفق است</td></tr>
    <tr><td>PaymentVerify</td><td>50-</td><td>Session is not valid, amounts values is not the same.</td><td>مبلغ وریفای با مبلغ پرداختی متفاوت است</td></tr>
    <tr><td>PaymentRequest</td><td>41-</td><td>Maximum amount is 100,000,000 tomans.</td><td>حداکثر مبلغ ۱۰۰٬۰۰۰٬۰۰۰ تومان است</td></tr>
    <tr><td>PaymentRequest</td><td>40-</td><td>Invalid extra params, expire_in is not valid.</td><td>پارامتر اضافی نامعتبر است</td></tr>
    <tr><td>PaymentRequest</td><td>39-</td><td>Wages have a error!</td><td>خطا در تسهیم</td></tr>
    <tr><td>PaymentRequest</td><td>38-</td><td>Wages need to set Iban in shaparak.</td><td>عدم تعریف صحیح شبا در شاپرک</td></tr>
    <tr><td>PaymentRequest</td><td>37-</td><td>One or more iban entered for wages(floating) from the bank side are inactive.</td><td>یک یا چند شماره شبا غیرفعال است</td></tr>
    <tr><td>PaymentRequest</td><td>36-</td><td>The minimum amount for wages(floating) should be 10,000 Rials</td><td>حداقل مبلغ هر تسهیم ۱۰٬۰۰۰ ریال است</td></tr>
    <tr><td>PaymentRequest</td><td>35-</td><td>Wages is not valid, Total wages(floating) has been reached the limit in max parts.</td><td>تعداد دریافت‌کنندگان بیش از حد مجاز است</td></tr>
    <tr><td>PaymentRequest</td><td>34-</td><td>Wages is not valid, Total wages(fixed) has been overload max amount.</td><td>مجموع تسهیم از مبلغ کل بیشتر است</td></tr>
    <tr><td>PaymentRequest</td><td>33-</td><td>Wages floating is not valid.</td><td>درصدهای وارد شده صحیح نیست</td></tr>
    <tr><td>PaymentRequest</td><td>32-</td><td>Wages is not valid, Total wages(floating) has been overload max amount.</td><td>مجموع تسهیم از مبلغ کل بیشتر است</td></tr>
    <tr><td>PaymentRequest</td><td>31-</td><td>Terminal do not allow to accept wages, please add default bank account in panel.</td><td>حساب بانکی تسویه در پنل ثبت نشده است</td></tr>
    <tr><td>PaymentRequest</td><td>30-</td><td>Terminal do not allow to accept floating wages.</td><td>ترمینال اجازه تسویه اشتراکی شناور ندارد</td></tr>
    <tr><td>public</td><td>19-</td><td>Terminal user transactions are banned.</td><td>امکان ایجاد تراکنش برای این ترمینال وجود ندارد</td></tr>
    <tr><td>public</td><td>18-</td><td>The referrer address does not match the registered domain.</td><td>امکان استفاده کد درگاه اختصاصی روی دامنه دیگر وجود ندارد</td></tr>
    <tr><td>public</td><td>17-</td><td>Terminal user level is not valid : ( please contact our support team).</td><td>محدودیت پذیرنده در سطح آبی</td></tr>
    <tr><td>public</td><td>16-</td><td>Terminal user level is not valid : ( please contact our support team).</td><td>سطح تایید پایین‌تر از نقره‌ای است</td></tr>
    <tr><td>public</td><td>15-</td><td>Terminal user is suspend : (please contact our support team).</td><td>درگاه تعلیق شده است</td></tr>
    <tr><td>public</td><td>14-</td><td>The callback URL domain does not match the registered terminal domain.</td><td>دامنه کال‌بک با دامنه ثبت‌شده مغایرت دارد</td></tr>
    <tr><td>public</td><td>13-</td><td>Terminal limit reached.</td><td>محدودیت تراکنش</td></tr>
    <tr><td>public</td><td>12-</td><td>To many attempts, please try again later.</td><td>تلاش بیش از حد مجاز</td></tr>
    <tr><td>public</td><td>11-</td><td>Terminal is not active, please contact our support team.</td><td>مرچنت کد فعال نیست</td></tr>
    <tr><td>public</td><td>10-</td><td>Terminal is not valid, please check merchant_id or ip address.</td><td>مرچنت کد یا آی‌پی معتبر نیست</td></tr>
    <tr><td>public</td><td>9-</td><td>Validation error</td><td>خطای اعتبارسنجی</td></tr>
    <tr><td>public</td><td>5-</td><td>Referrer ID is invalid</td><td>کد معرف نامعتبر است</td></tr>
    <tr><td>public</td><td>4-</td><td>Amount is invalid</td><td>مبلغ خارج از محدوده مجاز است</td></tr>
    <tr><td>public</td><td>3-</td><td>Description is required or too long</td><td>توضیحات وارد نشده یا بیشتر از ۵۰۰ کاراکتر است</td></tr>
    <tr><td>public</td><td>2-</td><td>Callback URL is required</td><td>آدرس بازگشت وارد نشده است</td></tr>
    <tr><td>public</td><td>100</td><td>Success</td><td>عملیات موفق</td></tr>
    <tr><td>PaymentVerify</td><td>101</td><td>Verified</td><td>تراکنش قبلا وریفای شده است</td></tr>
    <tr><td>public</td><td>429</td><td>Referrer code format is invalid.</td><td>قالب کد معرف نامعتبر است</td></tr>
  </tbody>
</table>

<h3>Exceptionهای SDK</h3>
<ul>
  <li><code dir="ltr">ZarinpalValidationException</code>: ورودی با قوانین معتبر نیست و قبل از ارسال به درگاه خطا گرفته می‌شود</li>
  <li><code dir="ltr">ZarinpalApiException</code>: درگاه پاسخ خطا داده است و شامل کد و پیام درگاه است</li>
  <li><code dir="ltr">ZarinpalTransportException</code>: خطای شبکه یا تایم‌اوت در ارتباط با درگاه</li>
  <li><code dir="ltr">ZarinpalCallbackException</code>: پارامترهای بازگشتی ناقص یا نامعتبر هستند</li>
</ul>

<p>برای خطایابی شبکه، ابتدا <span dir="ltr">base-url</span>ها را بررسی کنید، زمان‌های <span dir="ltr">timeout</span> را افزایش دهید و در محیط <span dir="ltr">SANDBOX</span> تست کنید.</p>

<h2>پرسش‌های پرتکرار</h2>
<ul>
  <li>چرا درخواست پرداخت خطای اعتبارسنجی می‌دهد؟ بررسی کنید <span dir="ltr">merchant-id</span> و <span dir="ltr">callback-url</span> مقدار دارند و توضیح تراکنش خالی نیست.</li>
  <li>چرا وریفای خطای <span dir="ltr">-54</span> می‌دهد؟ مقدار <span dir="ltr">authority</span> نامعتبر است یا اشتباه ارسال شده است.</li>
  <li>چرا ریورس خطای <span dir="ltr">-62</span> می‌دهد؟ آی‌پی سرور در پنل ثبت نشده است.</li>
  <li>چرا پرداخت موفق بوده اما کد <span dir="ltr">101</span> می‌گیرم؟ تراکنش قبلا وریفای شده است.</li>
  <li>چرا <span dir="ltr">referrer_id</span> اعمال نمی‌شود؟ ممکن است کد معرف معتبر نباشد یا شرایط ترمینال اجازه ثبت آن را ندهد.</li>
</ul>

</div>
